<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="WebEval.aspx.cs" Inherits="WebEval.WebEval" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>UNCW WebEval</title>
    <link href="Content/bootstrap.css" rel="stylesheet" type="text/css" />
    <script>
        function moveQuestion(direction) {
            if (direction = "R") {
                count += 1;
            }
            else {
                count -= 1;
            }
            if (count < 0) {
                count = 0;
            }
            else if (count > questionSet.length) {
                count = questionSet.length;
            }
            document.getElementById("display_question").innerHTML = questionSet[count];
        }
    </script>
    </head>
<body style="height: 605px">
        <script>
	var count = 0;
	var questionSet = ["Please describe a system briefly.", "How can nurses use this system to meet the aims of “Support Safety and Quality of Nursing”?", "How can nurses use this system to meet the aims of “Facilitate Continuity of Care and Care Coordination”?", "How can nurses use this system to meet the aims of “Partner with Patients and Families to participate in Health care”?", "Tell me about possible “Unexpected Outcomes” of this system ", "Any suggestions to improve “Unexpected Outcomes” this system?"];
	var text = "";
	var i;
	for (i = 0; i < questionSet.length; i++) {
		text += (i+1)+") " + questionSet[i] + "<br>";
	}
	document.getElementById("display_question").innerHTML = text;
    
    </script>
    <form id="form1" runat="server">
    <div>    
        <h1 style="text-align: left">
            <asp:Image ID="Image1" runat="server" Height="87px" ImageUrl="house_teal.gif" style="text-align: left" Width="156px" />
            <asp:Label ID="Label1" runat="server" Text="WebEval"></asp:Label>
        </h1>
        <p style="text-align: left">
            <asp:Label runat="server" ID="lblIdNumber" Visible="False">UNCW Id Number</asp:Label>
            <br />
            <asp:TextBox ID="dblUNCWId" class="form-control" placeholder="Enter 850 number" runat="server" Visible="False"></asp:TextBox>
            <asp:Button ID="btnLoginSubmit" class="btn-success" runat="server" Text="Submit" Width="112px" OnClick="btnLoginSubmit_Click" Visible="False" UseSubmitBehavior="False" />
        </p>
        <p id="display_question" align="middle">Press next to display first or next question.</p>

        <section class="main-controls">
            <canvas class="visualizer" height="60px"></canvas>
            <div id="questions" align="center">
                <button class="btn btn-success" id="btnPrev" type="button" onclick="moveQuestion(L);">Previous Question</button>
                <button class="btn btn-success" id="btnNext" type="button" onclick="moveQuestion(R);">Next Question</button>
            </div>
                <!--Space div for space between buttons-->
                <div class="divider" style="margin-bottom: 3em;">
                </div>
                <div id="buttons" align="center">
                    <button class="btn btn-success">Record</button>
                    <button class="btn btn-success">Stop Recording</button>
                </div>

                <!--form action="/index.html" method="POST">
                    <input type="file" name="recording" accept="audio/*">
                    <input type="submit">
                </form-->
        </section>
    </div>

</form>
</body>
</html>
