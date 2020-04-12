package Chat

import Chat.Tokens._
import Tree._

import scala.collection.mutable.ListBuffer

// TODO - step 4
class Parser(tokenizer: Tokenizer) {

  import tokenizer._

  var curTuple: (String, Token) = ("unknown", UNKNOWN)

  def curValue: String = curTuple._1

  def curToken: Token = curTuple._2

  /** Reads the next token and assigns it into the global variable curTuple */
  def readToken(): Unit = curTuple = nextToken()

  /** "Eats" the expected token, or terminates with an error. */
  private def eat(token: Token): Unit = if (token == curToken) readToken() else expected(token)

  /** Complains that what was found was not expected. The method accepts arbitrarily many arguments of type TokenClass */
  // TODO (BONUS): find a way to display the string value of the tokens (e.g. "BIERE") instead of their integer value (e.g. 6).
  private def expected(token: Token, more: Token*): Nothing =
    fatalError(" expected: " +
      (token :: more.toList).mkString(" or ") +
      ", found: " + curToken)

  def fatalError(msg: String): Nothing = {
    println("Fatal error", msg)
    new Exception().printStackTrace()
    sys.exit(1)
  }

  def getPrice: Any = {
    var orders: ListBuffer[(Int, String, String)] = ListBuffer()
    do {
      var tuple: (Int, String, String) = (null, null, null)
      tuple = tuple.copy(_1 = curTuple._1.toInt)
      eat(NUM)

      tuple = tuple.copy(_2 = curTuple._1)
      if (curToken == BIERE) eat(BIERE)
      if (curToken == CROISSANT) eat(CROISSANT)

      if (curToken == BRAND) {
        tuple = tuple.copy(_3 = curTuple._1)
        eat(BRAND)
      }
      orders.append(tuple)
    } while(curToken != EOL)
  }

  /** the root method of the parser: parses an entry phrase */
  def parsePhrases(): ExprTree = {
    if (curToken == BONJOUR) eat(BONJOUR)
    if (curToken == JE) eat(JE)
    if (curToken == USELESS) eat(USELESS)
    if (curToken == ETRE) {
      eat(ETRE)
      if (curToken == ASSOIFFE) {
        // Here we do not "eat" the token, because we want to have a custom 2-parameters "expected" if the user gave a wrong token.
        readToken()
        Thirsty()
      }
      else if (curToken == AFFAME) {
        readToken()
        Hungry()
      } else if (curToken == PSEUDO) {
        Identification(curTuple._1.slice(1, curTuple._1.length))
      }
      else expected(ASSOIFFE, AFFAME)
    } else if (curToken == APPELER) {
      eat(APPELER)
      Identification(curTuple._1.slice(1, curTuple._1.length))
    } else if (curToken == COMBIEN) {
      eat(COMBIEN)
      eat(COUTER)
      getPrice()
    } else if (curToken == QUEL) {
      eat(ETRE)
      eat(USELESS)
      eat(PRIX)
      eat(USELESS)
      getPrice()
    }
    else expected(BONJOUR, JE)
  }
  // Start the process by reading the first token.
  readToken()

}
