package Chat

import Data.{Products, UsersInfo}

import math.min

object Tree {

  /**
    * This sealed trait represents a node of the tree and contains methods to compute it and write its output text in console.
    */
  sealed trait ExprTree {
    /**
      * Compute the price of the current node, then returns it. If the node is not a computational node, the method
      * returns 0.0.
      * For example if we had a "+" node, we would add the values of its two children, then return the result.
      *
      * @return the result of the computation
      */
    def computePrice: Double = this match {
      // Products
      case Beer(number: Int, brand: String) => number * Products.getBeer(brand)
      case Croissant(number: Int, brand: String) => number * Products.getCroissant(brand)

      // Operators
      case And(e1: ExprTree, e2: ExprTree) => e1.computePrice + e2.computePrice
      case Or(e1: ExprTree, e2: ExprTree) => min(e1.computePrice, e2.computePrice)

      // Default
      case _ => 0.0
    }

    /**
      * Return the output text of the current node, in order to write it in console.
      *
      * @return the output text of the current node
      */
    def reply: String = this match {
      // Example cases
      case Thirsty() => "Eh bien, la chance est de votre côté, car nous offrons les meilleures bières de la région !"
      case Hungry() => "Pas de soucis, nous pouvons notamment vous offrir des croissants faits maisons !"

      // Request cases
      case Price(e: ExprTree) => s"Cela coûte CHF ${e.computePrice}"
      case Identification(pseudo) =>
        UsersInfo.setActiveUser(pseudo)
        s"Bonjour $pseudo !"
      case Order(e: ExprTree) =>
        if (UsersInfo.isThereAnActiveUser) {
          try {
            UsersInfo.purchase(e.computePrice)
            s"Voici donc ${e.reply} ! ${Price(e).reply} et votre nouveau solde est de CHF ${UsersInfo.getBalance()}."
          } catch  {
            case e: Error => "Solde insuffisant."
          }
        } else {
          "Veuillez d'abord vous identifier."
        }
      case Balance() =>
        if (UsersInfo.isThereAnActiveUser)
          s"Le montant actuel de votre solde est de CHF ${UsersInfo.getBalance()}."
        else
          "Veuillez d'abord vous identifier."

      // Product cases
      case Beer(number: Int, brand: String) => s"${number} ${if (brand != "") brand else "boxer"}"
      case Croissant(number: Int, brand: String) => s"${number} croissant ${if (brand != "") brand else "maison"}"

      // Operator cases
      case Or(e1: ExprTree, e2: ExprTree) =>
        // Only print the cheapest option
        if (e1.computePrice < e2.computePrice) e1.reply
        else e2.reply
      case And(e1: ExprTree, e2: ExprTree) => s"${e1.reply} et ${e2.reply}"
    }
  }

  /**
    * Declarations of the nodes' types.
    */
  // Example cases
  case class Thirsty() extends ExprTree
  case class Hungry() extends ExprTree

  // Request cases
  case class Order(e: ExprTree) extends ExprTree
  case class Balance() extends ExprTree
  case class Price(e: ExprTree) extends ExprTree
  case class Identification(pseudo: String) extends ExprTree

  // Operator cases
  case class And(e1: ExprTree, e2: ExprTree) extends ExprTree
  case class Or(e1: ExprTree, e2: ExprTree) extends ExprTree

  // Product cases
  case class Beer(number: Int, brand: String) extends ExprTree
  case class Croissant(number: Int, brand: String) extends ExprTree

}
