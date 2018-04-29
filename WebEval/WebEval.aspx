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
            <asp:ImageButton ID="btnLeft" UseSubmitBehavior="false" runat="server" ImageUrl="images/left_arrow.png" Height="77px" Width="173px" OnClick="left_arrow_Click"/>
            <div class="divider"></div>

            <asp:ImageButton ID="btnRight" UseSubmitBehavior="false" runat="server" Height="77px" ImageUrl="images/right_arrow.png" Width="173px" OnClick="right_arrow_Click" />

                <br />
                <asp:ImageButton ID="btnRecord" runat="server" Height="70px" ImageUrl="images/record_button.png" Width="70px" OnClientClick="startRecording()"/>
                <div class="divider"></div>
                <asp:ImageButton ID="btnStop" runat="server" Height="70px" ImageUrl="images/stop_button_disabled.png" Width="70px" OnClientClick="stopRecording()" />
                <div class="divider"></div>
                <asp:ImageButton ID="btnPlay" runat="server" Height="70px" ImageUrl="images/play_button_disabled.png" Width="70px" OnClientClick="playRecording()" />
                <div class="divider"></div>
                <asp:ImageButton ID="btnReset" runat="server" Height="70px" ImageUrl="images/reset_button_disabled.png" Width="70px" OnClientClick="restartRecording()" />

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
    <script type="text/javascript">

          var mic;
          var recorder;

          //Soundfile is a p5.soundfile() class object.
          //Most likely you can just store this as a Json inside the database.
          //In order to play the soundfile, just do sound_file.play();
          var sound_file;

          /**   Initliazes the the mic, recorder, and soundfile variables.
            *    Also asks user for permission to use microphone.
            */
          function setup(){
            mic = new p5.AudioIn();
            mic.start();
            recorder = new p5.SoundRecorder();
            recorder.setInput(mic);
            sound_file= new p5.SoundFile();

          }

           /**  
                Starts the recording process, only works if mic is enabled, otherwise a pop up still tell you your mic isn't enabled.
                Also disables the record button(your already recording), play button, and reset button.
            */
          function startRecording(){
            console.log("start Recording ....");
            if(mic.enabled){

                recorder.record(sound_file)
                document.getElementById("btnRecord").disabled = true;
                document.getElementById("btnRecord").src = "images/record_button_disabled.png";
                document.getElementById("btnStop").disabled = false;
                document.getElementById("btnStop").src = "images/stop_button.png";
                document.getElementById("btnPlay").src = "images/play_button_disabled.png";
                document.getElementById("btnPlay").disabled = true;
                document.getElementById("btnReset").disabled = true;
                

                }
            else{
                window.alert("Mic is not enabled");
                }
          }

            /**
               Stops the recording process.
               Enables all recorder-related buttons.
            */
          function stopRecording(){
            console.log("Stop Recording.....");
            recorder.stop();

            document.getElementById("btnRecord").disabled = false;
            document.getElementById("btnRecord").src = "images/record_button.png";
            document.getElementById("btnPlay").disabled = false;
            document.getElementById("btnPlay").src = "images/play_button.png";
            document.getElementById("btnStop").disabled = true;
            document.getElementById("btnStop").src = "images/stop_button_disabled.png";
            document.getElementById("btnReset").disabled = false;
            document.getElementById("btnReset").src = "images/reset_button.png";
            saveSound(sound_file, 'tempSound.wav');

          }
            
            /** 
                Plays the current recording.
            */
          function playRecording(){
            sound_file.play();
            console.log("play Recording....");
          }
            
            /**
                restarts the recording process.
                Also sets the play button, stop button, and reset button to disabled, since there is no recording to interact with.
            */
          function restartRecording(){
            recorder._clear();
            sound_file = new p5.SoundFile();
            console.log("Erase Recording....");

            document.getElementById("btnRecord").disabled = false;
            document.getElementById("btnPlay").disabled = true;
            document.getElementById("btnPlay").src = "images/play_button_disabled.png";
            document.getElementById("btnStop").disabled = true;
            document.getElementById("btnStop").src = "images/stop_button_disabled.png";
            document.getElementById("btnReset").disabled = true;
            document.getElementById("btnReset").src = "images/reset_button_disabled.png";

          }

          setup();
    </script>

</body>
</html>
