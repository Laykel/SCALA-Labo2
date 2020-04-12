package Chat

object Tokens {
  type Token = Int

  // Terms
  val BONJOUR: Token     = 0
  val JE: Token          = 1
  val SOLDE: Token       = 26
  val PRIX: Token        = 30
  val USELESS: Token     = 31
  // Asks
  val COMBIEN: Token     = 27
  val QUEL: Token        = 28
  // Actions
  val ETRE: Token        = 2
  val VOULOIR: Token     = 3
  val APPELER: Token     = 23
  val COMMANDER: Token   = 24
  val CONNAITRE: Token   = 25
  val COUTER: Token      = 29
  // Operators
  val ET: Token          = 4
  val OU: Token          = 5
  // Products
  val BIERE: Token       = 6
  val CROISSANT: Token   = 7
  // Utils
  val PSEUDO: Token      = 9
  val NUM: Token         = 10
  val UNKNOWN: Token     = 11
  val EOL: Token         = 12
  // State of mind
  val ASSOIFFE: Token    = 13
  val AFFAME: Token      = 14
  // Brands
  val BRAND: Token       = 15
//  val MAISON: Token      = 15
//  val CAILLER: Token     = 16
//  val FARMER: Token      = 17
//  val BOXER: Token       = 18
//  val WITTEKOP: Token    = 19
//  val PUNKIPA: Token     = 20
//  val JACKHAMMER: Token  = 21
//  val TENEBREUSE: Token  = 22
}
