﻿<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="main.aspx.cs" Inherits="WebEval.main" %>

<!DOCTYPE html>

<html lang="en-us">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width">
    <title>WebEval</title>
    <link href="Content/bootstrap.css" rel="stylesheet" type="text/css">


    <style type='text/css'>
        ul {
            list-style: none;
        }

        #recordingslist audio {
            display: block;
            margin-bottom: 10px;
        }
    </style>



</head>
<body>

    <h2>Recordings</h2>
    <ul id="recordingslist"></ul>

    <h2>Log</h2>
    <pre id="log"></pre>




    <script>
        var count = 0;
        var questionSet = ["Please describe a system briefly.", "How can nurses use this system to meet the aims of “Support Safety and Quality of Nursing”?", "How can nurses use this system to meet the aims of “Facilitate Continuity of Care and Care Coordination”?", "How can nurses use this system to meet the aims of “Partner with Patients and Families to participate in Health care”?", "Tell me about possible “Unexpected Outcomes” of this system ", "Any suggestions to improve “Unexpected Outcomes” this system?"];
        var text = "";
        var i;
        for (i = 0; i < questionSet.length; i++) {
            text += (i + 1) + ") " + questionSet[i] + "<br>";
        }
        document.getElementById("display_question").innerHTML = text;
    </script>

    <div class="wrapper">

        <header>
            <h1 align="middle">WebEval</h1>
        </header>
        <p id="display_question" align="middle">Press next to display first or next question.</p>

        <section class="main-controls">
            <canvas class="visualizer" height="60px"></canvas>
            <div id="questions" align="center">
                <button class="btn btn-success" type="button" onclick='document.getElementById("display_question").innerHTML = questionSet[count - 1], count -= 1, restartRecording();'>Previous Question</button>
                <button class="btn btn-success" type="button" onclick='document.getElementById("display_question").innerHTML = questionSet[count], count += 1, restartRecording();'>Next Question</button>
            </div>
            <!--Space div for space between buttons-->
            <div class="divider" style="margin-bottom: 3em;">
            </div>
            <div id="buttons" align="center">
                <button id="recordButton" onclick="startRecording();" class="btn btn-success" >Record</button>
                <button id="stopButton" onclick="stopRecording();" class="btn btn-success" disabled>Stop Recording</button>
                <button id="playButton" onclick="playRecording();" class="btn btn-success" disabled>play</button>
                <button id="restartButton" onclick="restartRecording();" class="btn btn-success" disabled>restart</button>
            </div>

            <!--form action="/index.html" method="POST">
                <input type="file" name="recording" accept="audio/*">
                <input type="submit">
            </form-->
        </section>
        <!--p id="question_block" align="middle"></p-->

        <section class="sound-clips"></section>

    </div>

    <!--<label for="toggle">❔</label>
    <input type="checkbox" id="toggle">
    <aside>
        <h2>Information</h2>

        <p>WebEval was created using open source software courtesy of "Web dictaphone" which is built using <a href="https://developer.mozilla.org/en-US/docs/Web/API/Navigator.getUserMedia">getUserMedia</a> and the <a href="https://developer.mozilla.org/en-US/docs/Web/API/MediaRecorder_API">MediaRecorder API</a>, which provides an easier way to capture Media streams.</p>

        <p>If you are unable to record, save or find a previously saved recording please contact the developer at bcb7611@uncw.edu!</p>

        <p>
            Helpful Instructions:
            <ol id="helpful_info">
                <li>Press 'Record' to begin recording, and press stop to stop recording.</li>
                <li>Make sure to navigate through the questions using the 'next' and 'prev' buttons</li>
                <li>To ask for help via email contact tmt5249@uncw.edu</li>
            </ol>
        </p>-->
    <!--form action="/index.html" method="POST">
        <input type="file" name="recording" accept="audio/*">
        <input type="submit">
    </form-->
    <!--button id="install-btn">Install app "coming soon"</button-->
    </aside>

  
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
            document.getElementById("recordButton").disabled = true;
            document.getElementById("stopButton").disabled = false;
            document.getElementById("playButton").disabled = true;
            document.getElementById("restartButton").disabled = true;
            __log('Recording...');
        }

        function stopRecording() {
            recorder && recorder.stop();
            document.getElementById("recordButton").disabled = false;
            document.getElementById("playButton").disabled = false;
            document.getElementById("stopButton").disabled = false;
            document.getElementById("restartButton").disabled = false;


            __log('Stopped recording.');

            // create WAV download link using audio data blob


        }

        function restartRecording() {
            document.getElementById("recordButton").disabled = false;
            document.getElementById("playButton").disabled = true;
            document.getElementById("stopButton").disabled = true;
            document.getElementById("restartButton").disabled = true;


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
