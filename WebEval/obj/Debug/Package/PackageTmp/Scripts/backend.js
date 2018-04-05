
//List of all our quiz questions
var questions = ["Please Describe a system briefly", "How can nurses use this system to meet the aims of “Support Safety and Quality of Nursing”?", "How can nurses use this system to meet the aims of “Facilitate Continuity of Care and Care Coordination”?", "How can nurses use this system to meet the aims of “Partner with Patients and Families to participate in Health care”?", "Tell me about possible “Unexpected Outcomes” of this system", "Any suggestions to improve “Unexpected Outcomes” this system?"];

//Index of our current question on the quiz
var questionCounter = 0;

//Links to the Textbox containing the current question on the index.html page
var currentQuestion = document.getElementById("questionsText");

/**
 *  Function used to check ID submission for validation.
    Sets questions ,which are on the same page, to visible once password is validated.
 */
function loginSubmit() {
    var loginBlock = document.getElementById("loginBlock");
    var questionBlock = document.getElementById("questions");
    var recordingBlock = document.getElementById("recording");
    var loginID = document.getElementById("dblUNCWId");

    //Sets the quiz elements to visible, and the login page to hidden
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

/**
 *  Quiz questions are kept as a list.
    Moves onto the previous question by moving the current quiz question index by -1
 */
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

/**
 *  Quiz questions are kept in a list.
    Moves onto the next question by moving the current quiz question index by +1
 */
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