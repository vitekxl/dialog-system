package models.items.phrase

import models.Answer
import models.items.runner.DebugRunner
import tools.AnswersTool
import java.lang.invoke.SerializedLambda

open class DebugFilteredPhrase(id: String, phrases: Array<String>,  answers: Array<Answer>): AFilteredPhrase(id, phrases, answers){

    //constructor(id: String, phrase: String,  answers: Array<Answer>) : this(id, arrayOf(phrase), answers)
   // constructor(filteredPhrase: FilteredPhrase) : this(filteredPhrase.id, filteredPhrase.phrases, filteredPhrase.answers)

     init {
         this.runner = DebugRunner();
     }

    final override fun body(): Answer {
        incrementCounter()
        val answers = AnswersTool.copyArrayOrAnswers(this.answers)
        val phrases = this.phrases.clone()

        var bodyAnswer = beforeFilter(answers, phrases, this)
        bodyAnswer = filter(bodyAnswer.answers, bodyAnswer.phrases)
        bodyAnswer = afterFilter(bodyAnswer.answers, bodyAnswer.phrases, this)

        val phrase = phraseChooser.choose(bodyAnswer.phrases)
        phrasePrinter.printTextDialog(phrase, bodyAnswer.answers)
        return answerChooser.chooseAnswer(bodyAnswer.answers)
    }

    public var beforeFilter : (answers: Array<Answer>, phrases: Array<String>, it: AFilteredPhrase) ->  FilterResult =
        { _, _, _ ->  FilterResult(answers, phrases)}
    public var afterFilter : (answers: Array<Answer>, phrases: Array<String>, it: AFilteredPhrase) ->  FilterResult  =
        { _, _, _ ->  FilterResult(answers, phrases)}
}