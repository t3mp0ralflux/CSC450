var questions = ["Please Describe a system briefly", "How can nurses use this system to meet the aims of “Support Safety and Quality of Nursing”?", "How can nurses use this system to meet the aims of “Facilitate Continuity of Care and Care Coordination”?", "How can nurses use this system to meet the aims of “Partner with Patients and Families to participate in Health care”?", "Tell me about possible “Unexpected Outcomes” of this system", "Any suggestions to improve “Unexpected Outcomes” this system?"];
var questionCounter = 0;
var currentQuestion = document.getElementById("questionsText");

function loginSubmit() {
    var loginBlock = document.getElementById("loginBlock");
    var questionBlock = document.getElementById("questions");
    var recordingBlock = document.getElementById("recording");
    var loginID = document.getElementById("dblUNCWId");


    if (loginID.value == "test"){
        loginBlock.style = "Visibility:hidden"
        questionBlock.style="Visibility:visible"
        recordingBlock.style = "Visibility:visible"
        currentQuestion.value = questions[0];
    }
    else {
        alert("You are an idiot")
    }
       
}

function right_arrow_Click() {
    if (questionCounter == -1) {
        questionCounter = 0;
    } else if (questionCounter == questions.length-1) {
            questionCounter = questions.length - 1;
            currentQuestion.value = questions[questionCounter];
    } else {
            questionCounter += 1;
            currentQuestion.value = questions[questionCounter];
    }
}

function left_arrow_Click() {
    if (questionCounter == -1) {
        questionCounter = 0;
    } else if (questionCounter == 0) {
        questionCounter = 0;
        currentQuestion.value = questions[questionCounter];
    } else {
        questionCounter -= 1;
        currentQuestion.value = questions[questionCounter];
    }
}