# TestTechnique
Ce projet est une API REST implémenté avec Spring boot maven.

### Requirements
- Java 17 doit être installé en local
- Installer un éditeur (intellij) pour executer le programme
- Le projet démarre sur le port 8080
- Avoir un API Client (POSTMAN)

### Fonctionnalités implémentés
1. Enregistrement d'un utilisateur ###### post: http://localhost:8080/testTechnique/api/user/registration
2. Enregistrement d'un client  ###### post: http://localhost:8080/testTechnique/api/client/registration
3. Authentification avec génération d'un token (avec JWT) ###### post: http://localhost:8080/testTechnique/api/user/connexion
4. Validation des emails.
5. Les exceptions personnalisés (RecordNotFoundException, TokenNotFoundException)
6. Les entités (Category, Client, TPE, User)
7. Les repositories (CategoryRepository, ClientRepository, TpeRepository, UserRepository)
8. Les controllers (ClientController, UserController)
9. Les services (ClientService, UserService)
