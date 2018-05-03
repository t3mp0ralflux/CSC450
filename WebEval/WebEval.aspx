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
            <asp:Image ID="Image1" runat="server" Height="87px" ImageUrl="~/Models/images/house_teal.gif" style="text-align: left" Width="156px" />
            <asp:Label ID="lblTitle" runat="server" Text="WebEval"></asp:Label>
        </h1>
        <p style="text-align: left">
            <asp:Label runat="server" ID="lblIdNumber" Visible="False">UNCW Id Number</asp:Label>
            <br />
            <asp:TextBox ID="dblUNCWId" class="form-control" placeholder="Enter 850 number" runat="server" Visible="False"></asp:TextBox>
            <asp:Button ID="btnLoginSubmit" class="btn-success" runat="server" Text="Submit" Width="112px" OnClick="btnLoginSubmit_Click" Visible="False" UseSubmitBehavior="False" />
            <asp:Button ID="btnLogout" CssClass="btn-success" runat="server" Text="Logout" Width="112px" Visible="False" UseSubmitBehavior="false" OnClick="btnLogout_Click" />
        </p>
        <div style="width: auto; margin:0 auto; text-align: center;">
            <asp:UpdatePanel ID="text_update" runat="server" UpdateMode="Always">
                <ContentTemplate>
            <asp:Label ID="lblQuestion" runat="server" Text="Press the right arrow to start the questionnaire."></asp:Label>
                    </ContentTemplate>
                </asp:UpdatePanel>
        </div>
        <div>
        <asp:updatepanel ID="button_update" runat="server" style="width:auto; margin:0 auto; text-align: center;" UpdateMode="Conditional">
            <ContentTemplate>
            <asp:ImageButton ID="btnLeft" UseSubmitBehavior="false" runat="server" ImageUrl="~/Models/images/left_arrow.png" Height="77px" Width="173px" OnClick="left_arrow_Click"/>
            <div class="divider"></div>

            <asp:ImageButton ID="btnRight" UseSubmitBehavior="false" runat="server" Height="77px" ImageUrl="~/Models/images/right_arrow.png" Width="173px" OnClick="right_arrow_Click" />

                <br />
                <asp:ImageButton ID="btnRecord" runat="server" Height="70px" ImageUrl="~/Models/images/record_button.png" Width="70px" OnClientClick="startRecording()"/>
                <div class="divider"></div>
                <asp:ImageButton ID="btnStop" runat="server" Height="70px" ImageUrl="~/Models/images/stop_button.png" Width="70px" OnClientClick="stopRecording()" />
                <div class="divider"></div>
                <asp:ImageButton ID="btnPlay" runat="server" Height="70px" ImageUrl="~/Models/images/play_button.png" Width="70px" OnClientClick="playRecording()" />
                <div class="divider"></div>
                <asp:ImageButton ID="btnReset" runat="server" Height="70px" ImageUrl="~/Models/images/reset_button_disabled.png" Width="70px" OnClientClick="restartRecording()" />
                <asp:ImageButton ID="btnUpload" runat="server" Height="70px" ImageUrl="~/Models/images/upload.png" Width="70px" OnClientClick="restartRecording()"/>
                        
                </ContentTemplate>
            </asp:updatepanel>
            </div>
        <div>
<asp:updatepanel ID="Previous_responses" runat="server" style="width:800px; margin:0 auto; text-align: center;" >
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
    <script src="scripts/backend.js"></script>
    <script src="scripts/p5/p5.min.js"></script>
    <script src="scripts/p5/addons/p5.dom.min.js"></script>
    <script src="Scripts/p5/addons/p5.sound.js"></script>
    


    <script src="scripts/recorder.js"></script>

    <script type="text/javascript">
        function nextQuestion() {
        }
        mic = new p5.AudioIn();

        var audio_context;
        var recorder;
        function startUserMedia(stream) {
            var input = audio_context.createMediaStreamSource(stream);
            console.log('Media stream created.');
            // Uncomment if you want the audio to feedback directly
            //input.connect(audio_context.destination);
            //__log('Input connected to audio context destination.');
            recorder = new Recorder(input);
            console.log('Recorder initialised.');
        }
        function startRecording() {
            recorder && recorder.record();
      
            console.log('Recording...');
        }
        function stopRecording() {
            recorder && recorder.stop();
            console.log('Stopped recording.');
            // create WAV download link using audio data blob
        }
        function restartRecording() {
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
                console.log('Audio context set up.');
                console.log('navigator.getUserMedia ' + (navigator.getUserMedia ? 'available.' : 'not present!'));
            } catch (e) {
                alert('No web audio support in this browser!');
            }
            navigator.getUserMedia({ audio: true }, startUserMedia, function (e) {
                console.log('No live audio input: ' + e);
            });
        };
    </script>

</body>
</html>
