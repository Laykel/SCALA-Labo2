package Chat

import Chat.Tokens._
import Utils.Dictionary.dictionary
import Utils.SpellChecker._

class Tokenizer(input: String) {
  var tokens: Array[(String, Token)] = Array()
  var currentTokenIndex: Token = -1

  private def getTokenFromString(s: String): Token = s match {
    case "bonjour" => BONJOUR
    case "je" => JE
    case "etre" => ETRE
    case "vouloir" => VOULOIR
    case "et" => ET
    case "ou" => OU
    case "biere" => BIERE
    case "croissant" => CROISSANT
    case "assoiffe" => ASSOIFFE
    case "affame" => AFFAME
    case "me" => USELESS
    case "mon" => USELESS
    case "appeler" => APPELER
    case "combien" => COMBIEN
    case "couter" => COUTER
    case "connaitre" => CONNAITRE
    case "quel" => QUEL
    case "le" => USELESS
    case "prix" => PRIX
    case "solde" => SOLDE
    case "de" => USELESS
    case "maison" => BRAND
    case "cailler" => BRAND
    case "farmer" => BRAND
    case "boxer" => BRAND
    case "wittekop" => BRAND
    case "punkipa" => BRAND
    case "jackhammer" => BRAND
    case "tenebreuse" => BRAND

    case p if p.startsWith("_") && p.length > 1 => PSEUDO // If the word starts with '_' and has more than one character it is a pseudonym.
    case n if n.forall(Character.isDigit) => NUM // If every character is a number, the word thus is a number.
    case _ => UNKNOWN
  }

  def tokenize(): Unit = {
    val words = input
      .trim()
      .replaceAll("[.,!?*]", " ") // Remove punctuation.
      .replaceAll(" +|[']", " ") // Remove multiple spaces and replace apostrophes by a space.
      .split(" ")
      .filterNot(_.isEmpty)

    // Get each word's occurrence in the dictionary or check for the closest word if it's not contained in the dictionary.
    val fromDictionary = words.map(w => dictionary.getOrElse(w, getClosestWordInDictionary(w)))

    tokens = fromDictionary.map(t => (t, getTokenFromString(t)))
  }

  def nextToken(): (String, Token) = {
    currentTokenIndex += 1

    if (currentTokenIndex < tokens.length) tokens(currentTokenIndex)
    else ("EOL", EOL)
  }
}
