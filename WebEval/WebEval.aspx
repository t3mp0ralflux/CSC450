<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="WebEval.aspx.cs" Inherits="WebEval.WebEval" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>UNCW WebEval</title>
    <link href="Content/bootstrap.css" rel="stylesheet" type="text/css" />
    </head>
<body style="height: 605px">
    <form id="form1" runat="server">
    <asp:ScriptManager runat="server" ID="sManager"></asp:ScriptManager>
    <div>    
        <h1 style="text-align: left">
            <asp:Image ID="Image1" runat="server" Height="87px" ImageUrl="images/house_teal.gif" style="text-align: left" Width="156px" />
            <asp:Label ID="lblTitle" runat="server" Text="WebEval"></asp:Label>
        </h1>
            <h2>Log</h2>
    <pre id="log"></pre>
        <p style="text-align: left">
            <asp:Label runat="server" ID="lblIdNumber" Visible="False">UNCW Id Number</asp:Label>
            <br />
            <asp:TextBox ID="dblUNCWId" class="form-control" placeholder="Enter 850 number" runat="server" Visible="False"></asp:TextBox>
            <asp:Button ID="btnLoginSubmit" class="btn-success" runat="server" Text="Submit" Width="112px" OnClick="btnLoginSubmit_Click" Visible="False" UseSubmitBehavior="False" />
        </p>
        <div style="width: 800px; margin:0 auto; text-align: center;">
            <asp:UpdatePanel ID="text_update" runat="server" UpdateMode="Always">
                <ContentTemplate>
            <asp:Label ID="lblQuestion" runat="server" Text="Press the right arrow to start the questionnaire."></asp:Label>
                    </ContentTemplate>
                </asp:UpdatePanel>
        </div>
        <div>
        <asp:updatepanel ID="button_update" runat="server" style="width:800px; margin:0 auto; text-align: center;" UpdateMode="Conditional">
            <ContentTemplate>
            <asp:ImageButton ID="btnLeft" UseSubmitBehavior="false" runat="server" ImageUrl="images/left_arrow.png" Height="77px" Width="173px" OnClick="left_arrow_Click"/>
            <div class="divider"></div>

            <asp:ImageButton ID="btnRight" UseSubmitBehavior="false" runat="server" Height="77px" ImageUrl="images/right_arrow.png" Width="173px" OnClick="right_arrow_Click" />

                <br />
                <asp:ImageButton ID="btnRecord" runat="server" Height="70px" ImageUrl="images/record_button.png" Width="70px" OnClientClick="startRecording()"/>
                <div class="divider"></div>
                <asp:ImageButton ID="btnStop" runat="server" Height="70px" ImageUrl="images/stop_button.png" Width="70px" OnClientClick="stopRecording()" />
                <div class="divider"></div>
                <asp:ImageButton ID="btnPlay" runat="server" Height="70px" ImageUrl="images/play_button.png" Width="70px" OnClientClick="playRecording()" />
                <div class="divider"></div>
                <asp:ImageButton ID="btnReset" runat="server" Height="70px" ImageUrl="images/pause_button.png" Width="70px" OnClientClick="resetRecording()" />

                </ContentTemplate>
            </asp:updatepanel>
            </div>
        <div>
<asp:updatepanel ID="Previous_responses" runat="server" style="width:800px; margin:0 auto; text-align: center;" UpdateMode="Conditional">
    <ContentTemplate>
        <asp:GridView ID="dgvResponses" runat="server">
        </asp:GridView>
    </ContentTemplate>
            </asp:updatepanel>
        </div>
    </div>


</form>
            <script src="scripts/install.js"></script>

    <script src="scripts/mediaDevices-getUserMedia-polyfill.js"></script>
    <!-- Below is your custom application script -->
    <script src="scripts/app.js"></script>
    <script src="scripts/recorder.js"></script>

    <script type="text/javascript">

        function nextQuestion() {

        }

        function __log(e, data) {
            log.innerHTML += "\n" + e + " " + (data || '');
        }

        var audio_context;
        var recorder;

        function startUserMedia(stream) {
            var input = audio_context.createMediaStreamSource(stream);
            __log('Media stream created.');

            // Uncomment if you want the audio to feedback directly
            //input.connect(audio_context.destination);
            //__log('Input connected to audio context destination.');

            recorder = new Recorder(input);
            __log('Recorder initialised.');
        }

        function startRecording() {
            recorder && recorder.record();
            document.getElementById("btnRecord").disabled = true;
            document.getElementById("btnStop").disabled = false;
            document.getElementById("btnPlay").disabled = true;
            document.getElementById("btnReset").disabled = true;
            __log('Recording...');
        }

        function stopRecording() {
            recorder && recorder.stop();
            document.getElementById("btnRecord").disabled = false;
            document.getElementById("btnPlay").disabled = false;
            document.getElementById("btnStop").disabled = false;
            document.getElementById("btnReset").disabled = false;


            __log('Stopped recording.');

            // create WAV download link using audio data blob


        }

        function restartRecording() {
            document.getElementById("btnRecord").disabled = false;
            document.getElementById("btnPlay").disabled = true;
            document.getElementById("btnStop").disabled = true;
            document.getElementById("btnReset").disabled = true;


            recorder && recorder.clear();
            recorder.clear();
        }

        function playRecording() {
            recorder && recorder.exportWAV(function (blob) {
                var url = URL.createObjectURL(blob);
                var audio = document.createElement('audio');
                audio.src = url;
                console.log(audio);
                audio.play();
            });
        }

        window.onload = function init() {
            try {
                // webkit shim
                window.AudioContext = window.AudioContext || window.webkitAudioContext;
                navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia;
                window.URL = window.URL || window.webkitURL;

                audio_context = new AudioContext;
                __log('Audio context set up.');
                __log('navigator.getUserMedia ' + (navigator.getUserMedia ? 'available.' : 'not present!'));
            } catch (e) {
                alert('No web audio support in this browser!');
            }

            navigator.getUserMedia({ audio: true }, startUserMedia, function (e) {
                __log('No live audio input: ' + e);
            });
        };

    </script>

</body>
</html>
