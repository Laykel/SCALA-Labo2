package Data

import scala.collection.mutable

object UsersInfo {
  // Will contain the name of the currently active user; default value is null.
  private var _activeUser: String = _

  private var accounts: scala.collection.mutable.Map[String, Double] = scala.collection.mutable.Map()

  /**
    * Update an account by decreasing its balance.
    *
    * @param user   the user whose account will be updated
    * @param amount the amount to decrease
    * @return the new balance
    */
  def purchase(user: String, amount: Double): Double = {
    if (accounts.contains(user)) {
      if (accounts(user) >= amount) {
        accounts(user) -= amount
      } else {
        throw new Error("Insufficient balance")
      }
    } else {
      throw new Error("Non-existing user")
    }
    accounts(user)
  }

  def addUser(user: String, amount: Double = 30.0): Any = {
    if (!accounts.contains(user)) {
      if (amount >= 0) {
        accounts += (user -> amount)
      } else {
        throw new Error("Negative amount")
      }
    } else {
      throw new Error("User already created")
    }
  }

  def getBalance(user: String = _activeUser): Double = {
    if (accounts.contains(user)) {
      accounts(user)
    } else {
      throw new Error("Non-existing user")
    }
  }

  def setActiveUser(user: String): Unit = {
    if (!accounts.contains(user)) {
      addUser(user)
    }
    _activeUser = user
  }
}
