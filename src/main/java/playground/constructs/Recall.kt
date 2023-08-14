package playground

fun recall(n: Int, function: () -> Unit) {
 // println(n)
  if (n <= 0) {
    function();
    return
  }
  recall(n - 1, function)
}
