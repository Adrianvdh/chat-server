# KoriChat
KoriChat is a client-server chat application. Basic features are supported such as user and
group management, sending direct messages between users and broadcasting messages in user groups.

## User management
User manegment allows new users to register and account, login as a user and the ability to list registered users.

### Registering a user
Register a new user, in this example we register the user adrian as a new user.

```
REG adrian CHAT/1.0
Name: Adrian van den Houten
```

The server will reply with one of the following responses:

If the registration is successful:
```
CHAT/1.O 201 Created
```
(This is user created on the server and can be used when ever you like. Users are recorded in a relational database.)

Or, if the user has already been registered.

```
CHAT/1.O 409 Conflict
```

### Logging in
Login with a user. This enables a person to login-to the user and start sending messages or doing other
stuff like creating groups.

here are some commands that require you to be logged in:
TODO


```
USE adrian CHAT/1.0
```

The server will reply with one of the following responses:

if logging in was successful, a session id will be returned.
Subsist requests to the server will require passing the session id to allow the server to identify the user.
```
CHAT/1.0 200 OK
Set-Cookie: SESSIONID: SOME SESSION ID
```

### Listing users
Listing registered users.

This is a helpful command that can be used to allow a user to easily be able to choose someone to direct message.

Listing all registered users:

```
LIST_USER CHAT/1.0
```
List users in a group. In this case out group's name is 'chat'
```
LIST_USER chat CHAT/1.0
```

The server will reply with one of the following responses:

If there are existing users already registered on the server, we shall get a response that looks like this:
```
CHAT/1.0 200 OK

adrian
josie
```
In this case, adrian and josie have been registered on the server.

## Group management

Create a group named 'chat' that is owned by user 'adrian'

```
MK_GROUP chat:adrian CHAT/1.0
```

Add josie to our 'chat' group and give her a user role.

```
ADD_USER chat CHAT/1.0
Invite: josie:user,john:user
```

## Messaging

Send a direct message to Josie

```
SEND josie CHAT/1.0

Hey josie! :)
```

Send message on a group
```
SEND chat

Hello everyone
```
## Authors

* **Adrian van den Houten** - *Initial work* - [Adrianvdh](https://github.com/Adrianvdh)
