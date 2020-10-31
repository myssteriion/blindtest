# Musics Blindtest

## Info Techiques
**nom appli:** musics-blindtest

| Quoi      | Version   |
| ---       | ---       |
| java      | 11        |
| gradle    | 6.6       |
| tomcat    | 9.0.37    |

### Build
Le projet est découpé en 3 sous-module: **conf**, **backend** et **frontend**.

#### conf
La conf possède un paramètre de build : **-Penv**.
- clean build -Penv="{dev|prod}" [-x test]

#### backend
- clean build [-x test]

#### frontend
- clean build

#### All
Il est possible de tout build d'un coup si le besoin est (notament pour packager la version PROD). Le dossier delivery contiendra tout ce qu'il faut.
- clean build -Penv="{dev|prod}" [-x test]

#### Paramétrage intelliJ
##### Tomcat 
|Ou             | Quoi                                                                              |
|---            | ---                                                                               |
| port          | 9500                                                                              |
| env           | SPRING_CONFIG_LOCATION=file:[...]/musics-blindtest/conf/dist/                     |
| VM options    | -Dlogging.config="[...]/musics-blindtest/conf/dist/musics-blindtest/logback.xml"  |

## Module Conf
La conf applicative du backend. Le paramètre de build permet de déterminer le dossier à utiliser. Le dossier **common** étant utilisé de base.

## Module Backend
### controller
- les controller REST (endpoints)

### model
- les entités
- les autres objets métiers
- les enums

### persistence
- les converters (map -> json string pour mettre en bdd)
- les DAO

### properties
- le mapping avec la conf

### security
- la couche sécurité

### services
- les services des entités (lien entre entité, dao, controller)
- les services des objets métiers

### tools
- les constantes 

## Module Frontend
### common
- ce qui est indépendant du métier

### game
- les composants relatifs à l'objet "game" + ces sous composants
- factoring-part : juste un dossier d'organisetion (contient les sous composants)

### home-view
- la home page

### interfaces
- les entités
- les autres objets métiers
- les enums

### params-view
- la vue paramétrage

### player
- les composants relatifs à l'objet "player"

### profile
- les composants relatifs à l'objet "profile"

### resources
- les resources (call les controllers backend)

### servives
- les services indépendant (toaster)

### statistics
- les composants relatifs à l'objet "statistics"

### tools
- les utilitaires (constantes, fonctions...)

