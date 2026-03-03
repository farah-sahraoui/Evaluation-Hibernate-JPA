# Évaluation Hibernate & JPA - Gestion de Stock, Projets et État Civil
Ce dépôt contient trois projets Java implémentant des concepts avancés de la persistance de données avec Hibernate et JPA (Java Persistence API).

## 📌 Sommaire
Exercice 1 : Gestion de Stock

Exercice 2 : Gestion de Projets

Exercice 3 : Gestion de l'État Civil

###  Exercice 1 : Gestion de Stock
Objectif : Gérer un catalogue de produits organisés par catégories et traiter les commandes clients.

🛠 Concepts clés :
Relation Many-to-One : Un Produit appartient à une seule Categorie.

Table d'association avec attributs : La classe LigneCommandeProduit gère la relation Many-to-Many entre Commande et Produit en y ajoutant l'attribut quantite.

Clé composée : Utilisation de @EmbeddedId pour identifier de manière unique chaque ligne de commande.

📊 Fonctionnalités :
Affichage des produits par catégorie.

Recherche de produits commandés entre deux dates.

Utilisation de requêtes nommées (Named Queries) pour filtrer par prix.



#### Exemples d'affichge:

<img width="562" height="313" alt="image" src="https://github.com/user-attachments/assets/1a6a280e-eafc-4d8d-812c-c70a3fe4cb69" />

<img width="465" height="65" alt="image" src="https://github.com/user-attachments/assets/cf7183ea-aa47-4e0e-9ba3-c8333e7b1413" />


<img width="632" height="403" alt="image" src="https://github.com/user-attachments/assets/6e44d6df-cea7-4687-a355-7f68dbc28ca3" />

<img width="457" height="35" alt="image" src="https://github.com/user-attachments/assets/405e65b1-8a4c-4fd3-91af-f2ab45011544" />

<img width="592" height="412" alt="image" src="https://github.com/user-attachments/assets/accb25ff-6579-4620-a54e-dac701ef2d4b" />

<img width="657" height="37" alt="image" src="https://github.com/user-attachments/assets/a605c431-206a-4981-b4cd-9bcbb71a1f74" />

###  Exercice 2 : Gestion de Projets
Objectif : Suivi des projets et des tâches assignées aux employés au sein d'un bureau d'études.

🛠 Concepts clés :
Relation "Chef de projet" : Un Employe peut diriger plusieurs Projet (One-to-Many).

Composition : Un Projet est composé de plusieurs Tache.

Attributs de liaison : La table de liaison EmployeTache stocke les dates de début et de fin réelles pour chaque affectation.

#### Exemples d'affichge:

![WhatsApp Image 2026-03-03 at 14 37 14](https://github.com/user-attachments/assets/89082327-9963-4ac7-91ee-0fba8bdcd007)


![WhatsApp Image 2026-03-03 at 14 40 27](https://github.com/user-attachments/assets/7d9e0e01-48dc-4484-bb8b-6a011e2f655f)


![WhatsApp Image 2026-03-03 at 14 41 17](https://github.com/user-attachments/assets/51184a6a-f6af-4429-9705-464c85bccb1d)


<img width="492" height="357" alt="image" src="https://github.com/user-attachments/assets/a479cd70-8114-4e9e-98c3-e30b96088c72" />

<img width="485" height="132" alt="image" src="https://github.com/user-attachments/assets/6a833f30-7ebc-4613-ac32-fde32779208a" />






###  Exercice 3 : Gestion de l'État Civil
Objectif : Modélisation des relations de parenté et des mariages entre citoyens.

🛠 Concepts clés :
Héritage (Inheritance) : Les classes Homme et Femme héritent de la classe abstraite Personne.

Relation Réflexive : Association entre Homme et Femme via la classe de liaison Mariage.

Gestion des cycles : Suivi des mariages passés et présents (dates de début, fin et nombre d'enfants).


<img width="836" height="623" alt="image" src="https://github.com/user-attachments/assets/141fcf7f-1374-4963-aecc-dfaceec3da4f" />


<img width="572" height="435" alt="image" src="https://github.com/user-attachments/assets/2c4a7001-8de4-40da-a3a7-1986e48085c3" />


<img width="593" height="667" alt="image" src="https://github.com/user-attachments/assets/0c98df51-85e0-495c-a9d2-56933b46ed7c" />


<img width="665" height="397" alt="image" src="https://github.com/user-attachments/assets/b146422f-65fd-4afa-b3e2-e02aec958beb" />


<img width="555" height="617" alt="image" src="https://github.com/user-attachments/assets/9b16ecd8-9083-4843-a6cb-dd7a1a6ca097" />


<img width="526" height="387" alt="image" src="https://github.com/user-attachments/assets/1da5380d-48d6-4bb6-af56-3009dd2dbc5c" />


<img width="453" height="58" alt="image" src="https://github.com/user-attachments/assets/c417f9bd-5d1c-4a0c-930e-68f70c359381" />

<img width="1068" height="196" alt="image" src="https://github.com/user-attachments/assets/e98f85cc-c8a5-4598-b01e-b506b1484776" />





⚙️ Configuration & Installation
Prérequis :

Java JDK 8+

MySQL Server

Maven / Hibernate 5.6.15.Final



## Conclusion:


Ce projet met en pratique les concepts fondamentaux de Hibernate et JPA à travers trois cas concrets. Il démontre la capacité à transformer 
des diagrammes de classes en une base de données fonctionnelle, en gérant efficacement les relations complexes (Many-to-Many avec attributs), 
l'héritage et l'intégrité des données. L'utilisation d'une architecture structurée (DAO/Services) et la maîtrise du cycle de vie des objets assurent
une application stable, capable de gérer des opérations de création, recherche et suppression sans erreurs techniques.
