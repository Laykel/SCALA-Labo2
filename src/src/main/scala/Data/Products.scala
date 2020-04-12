package Data

object Products {

  // Contains all the products and their price. Classified by type (beer or croissant)
  private val products: Map[String, Map[String, Double]] = Map(
    "Bières" ->
      Map("boxer" -> 1.0,
        "farmer" -> 1.0,
        "wittekop" -> 2.0,
        "punkipa" -> 3.0,
        "jackhammer" -> 3.0,
        "tenebreuse" -> 4.0),
    "Croissants" ->
      Map("maison" -> 2.0,
        "cailler" -> 2.0))

  /**
    * Get the price of a specific brand of beer. By default, get the boxer's price
    *
    * @param brand the brand of the beer
    * @return the price
    */
  def getBeer(brand: String): Double = {
    if (products("Bières").contains(brand)) {
      products("Bières")(brand)
    } else {
      products("Bières")("boxer")
    }
  }

  /**
    * Get the price of a specific type of croissant. By default, get the price of a "croissant maison"
    *
    * @param _type the type of the croissant
    * @return the price
    */
  def getCroissant(_type: String): Double = {
    if (products("Croissants").contains(_type)) {
      products("Croissants")(_type)
    } else {
      products("Croissants")("maison")
    }
  }
}
