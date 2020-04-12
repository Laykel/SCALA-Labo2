package Chat

import Data.{Products, UsersInfo}

import math.min

// TODO - step 3
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
      case Order(e: ExprTree) => s"Tu veux commander ${e.reply}"
      case Price(e: ExprTree) => s"Cela coûte CHF ${e.computePrice}."
      case Identification(pseudo) =>
        UsersInfo.setActiveUser(pseudo)
        s"Bonjour $pseudo !"
      // Product cases
      case Beer(_, brand: String) => brand
      case Croissant(_, brand: String) => s"croissant ${brand}"
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
