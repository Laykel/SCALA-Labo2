package Data

object Products {
  private val products: Map[String, Map[String, Double]] = Map(
    "Bières" ->
      Map("Boxer" -> 1.0,
        "Farmer" -> 1.0,
        "Wittekop" -> 2.0,
        "PunkIPA" -> 3.0,
        "Jackhammer" -> 3.0,
        "Ténébreuse" -> 4.0),
    "Croissants" ->
      Map("Maison" -> 2.0,
        "Cailler" -> 2.0))

  def getBeer(brand: String = "Boxer"): Double = {
    if (products("Bières").contains(brand)) {
      products("Bières")(brand)
    } else {
      throw new Error("Non existing beer")
    }
  }

  def getCroissant(_type: String = "Maison"): Double = {
    if (products("Croissants").contains(_type)) {
      products("Croissant")(_type)
    } else {
      throw new Error("Non existing croissant")
    }
  }
}
