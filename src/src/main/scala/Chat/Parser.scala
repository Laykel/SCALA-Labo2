package Chat

import Chat.Tokens._
import Tree._

import scala.collection.mutable.ListBuffer

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

  /**
    * Parse the following tokens as an order request and call buildTree
    *
    * @return The complete expression containing all expressions for the order
    */
  def parseOrder(): ExprTree = {
    val orders: ListBuffer[ExprTree] = ListBuffer()

    do {
      val number = curTuple._1.toInt
      eat(NUM)

      val product: Token = curToken
      eat(product)

      var brand: String = ""
      if (curToken == BRAND) {
        brand = curTuple._1
        eat(BRAND)
      }

      if (product == BIERE) {
        orders.append(Beer(number, brand))
      }
      else if (product == CROISSANT) {
        orders.append(Croissant(number, brand))
      }

      if (curToken == OU) {
        orders.append(Or(null, null))
        eat(OU)
      }
      else if (curToken == ET) {
        orders.append(And(null, null))
        eat(ET)
      }
    } while (curToken != EOL)

    buildTree(orders.toList)
  }

  /**
    * Converts a list of expressions into an expression tree
    *
    * @param l A list of Expressions to be built into a tree
    * @return The root expression of the tree
    */
  def buildTree(l: List[ExprTree]): ExprTree = l match {
    case x :: Nil => x
    case x1 :: x2 :: xs => x2 match {
      case Or(_, _) => buildTree(Or(x1, xs.head) :: xs.tail)
      case And(_, _) => buildTree(And(x1, xs.head) :: xs.tail)
    }
  }

  /** The root method of the parser: parses an entry phrase */
  def parsePhrases(): ExprTree = {
    // Start with optional niceties
    if (curToken == BONJOUR) eat(BONJOUR)
    if (curToken == JE) eat(JE)
    if (curToken == USELESS) eat(USELESS)

    // Then check for the main tokens
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
      }
      else if (curToken == PSEUDO) {
        Identification(curTuple._1.slice(1, curTuple._1.length))
      }
      else expected(ASSOIFFE, AFFAME, PSEUDO)
    }
    else if (curToken == APPELER) {
      eat(APPELER)
      if (curToken == PSEUDO) {
        Identification(curTuple._1.slice(1, curTuple._1.length))
      }
      else expected(PSEUDO)
    } else if (curToken == COMBIEN) {
      eat(COMBIEN)
      eat(COUTER)
      Price(parseOrder())
    }
    else if (curToken == QUEL) {
      eat(QUEL)
      eat(ETRE)
      eat(USELESS)
      eat(PRIX)
      eat(USELESS)
      Price(parseOrder())
    }
    else if (curToken == VOULOIR) {
      eat(VOULOIR)
      if (curToken == CONNAITRE) {
        eat(CONNAITRE)
        eat(USELESS)
        eat(SOLDE)
        Balance()
      }
      else if (curToken == COMMANDER) {
        eat(COMMANDER)
        Order(parseOrder())
      }
      else expected(CONNAITRE, COMMANDER)
    }
    else expected(BONJOUR, JE, ETRE, APPELER, COMBIEN, QUEL, VOULOIR)
  }

  // Start the process by reading the first token.
  readToken()

}
