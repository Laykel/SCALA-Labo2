package Data

object Products {
  private val products: Map[String, Map[String, Double]] = Map(
    "Bières" ->
      Map("boxer" -> 1.0,
        "farmer" -> 1.0,
        "wittekop" -> 2.0,
        "punkipa" -> 3.0,
        "jackhammer" -> 3.0,
        "ténébreuse" -> 4.0),
    "Croissants" ->
      Map("maison" -> 2.0,
        "cailler" -> 2.0))

  def getBeer(brand: String): Double = {
    if (products("Bières").contains(brand)) {
      products("Bières")(brand)
    } else {
      products("Bières")("boxer")
    }
  }

  def getCroissant(_type: String): Double = {
    if (products("Croissants").contains(_type)) {
      products("Croissants")(_type)
    } else {
      products("Croissants")("maison")
    }
  }
}
