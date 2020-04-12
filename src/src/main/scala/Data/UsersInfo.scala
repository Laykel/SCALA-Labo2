package Data

object UsersInfo {
  // Will contain the name of the currently active user; default value is null.
  private var _activeUser: String = _

  // Will contain all the accounts that have logged in the app
  private var accounts: scala.collection.mutable.Map[String, Double] = scala.collection.mutable.Map()

  /**
    * Update an account by decreasing its balance.
    *
    * @param user   the user whose account will be updated
    * @param amount the amount to decrease
    * @return the new balance
    */
  def purchase(amount: Double, user: String = _activeUser): Double = {
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

  /**
    * Add a new user to the registered accounts
    *
    * @param user   the username
    * @param amount the balance of the user
    */
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

  /**
    * Get the balance of an user
    *
    * @param user the username
    * @return the balance
    */
  def getBalance(user: String = _activeUser): Double = {
    if (accounts.contains(user)) {
      accounts(user)
    } else {
      throw new Error("Non-existing user")
    }
  }

  /**
    * To know if there is an active user
    *
    * @return true if there is, false if not
    */
  def isThereAnActiveUser: Boolean = if (_activeUser != null) true else false


  /**
    * Change the active user. If the user is not in the registered accounts, create a new account
    *
    * @param user the user name
    */
  def setActiveUser(user: String): Unit = {
    if (!accounts.contains(user)) {
      addUser(user)
    }
    _activeUser = user
  }
}
