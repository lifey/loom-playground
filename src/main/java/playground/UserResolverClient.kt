package playground


data class User(val email: String, val id: String, val city: String)
class UserResolverClient {
  fun findUserByEmail(email:String): User {
    Thread.sleep(100)
    return User(email,"1984","New Jersey")
  }

}
