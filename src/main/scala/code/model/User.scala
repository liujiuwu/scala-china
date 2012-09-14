package code.model

import net.liftweb.mapper.MegaProtoUser
import net.liftweb.mapper.MetaMegaProtoUser
import net.liftweb.mapper.MappedDateTime
import net.liftweb.mapper.MappedString
import net.liftweb.http.S

class User extends MegaProtoUser[User] {
  def getSingleton = User

  /*t.string "email", :null => false
    t.string "name", :null => false
    t.string "location"
    t.string "bio"
    t.string "website"
    t.string "avatar_file_name"
    t.boolean "verified", :default => false, :null => false
    t.integer "state", :default => 1, :null => false
    t.string "qq"
    t.string "tagline"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string "crypted_password", :default => "", :null => false
    t.string "password_salt", :default => "", :null => false
    t.string "persistence_token", :default => "", :null => false
    t.string "single_access_token", :default => "", :null => false
    t.string "perishable_token", :default => "", :null => false
    t.integer "login_count", :default => 0, :null => false
    t.integer "failed_login_count", :default => 0, :null => false
    t.datetime "last_login_at"
    t.string "current_login_ip"
    t.string "last_login_ip"
    t.integer "notes_count", :default => 0, :null => false*/

  object qq extends MappedString(this, 20)
  object createdAt extends MappedDateTime(this)

  override lazy val firstName = new MyFirstName(this, 32) {
    println("I am doing something different")
  }
}

object User extends User with MetaMegaProtoUser[User] {
  override def dbTableName = "users"
  override def fieldOrder = List(id, email, locale, timezone, password)
  
  override def signupFields = List(email, password)
  
  override def loginXhtml ={
		<lift:surround with="login" at="content">
		<h1></h1> 
		<form action={S.uri} method="POST" accept-charset="utf-8">
	 	<lift:msgs showAll="true" />
		<p id="username-field"> 
		<label for="username">Email:</label> 
		<user:email />
		</p> 
		<p id="password-field"> 
		<label for="password">密码:</label> 
		<user:password />
		</p> 
		<p id="signin-field"> 
		<user:submit />
		</p> 
		
		</form>
	  </lift:surround>
	}
}
