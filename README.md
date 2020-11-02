# Musics Blindtest

## Info Techiques
**nom appli:** musics-blindtest

| Quoi      | Version   |
| ---       | ---       |
| postgres  | 12        |
| java      | 11        |
| gradle    | 6.7       |
| tomcat    | 9.0.37    |
| node      | 13.9.0    |
| npm       | 6.13.7    |
| httpd     | 2.4       |

### Build
Le projet est découpé en 3 sous-module: **conf**, **backend** et **frontend**.

| Module    | Commande                                  |
| ---       | ---                                       |
| conf      | clean build -Penv="{dev/prod}"            |
| backend   | clean build [-x test]                     |
| frontend  | clean build                               |
| root      | clean build -Penv="{dev/prod}" [-x test]  |

### Paramétrage IntelliJ
#### Tomcat 
| Clé           | Valeur                                                                              |
| ---           | ---                                                                               |
| port          | 9500                                                                              |
| env           | SPRING_CONFIG_LOCATION=file:[...]/musics-blindtest/conf/dist/                     |
| VM options    | -Dlogging.config="[...]/musics-blindtest/conf/dist/musics-blindtest/logback.xml"  |

## Module Conf
La conf applicative du backend. Le paramètre de build permet de déterminer le dossier à utiliser. Le dossier **common** étant utilisé de base.

## Module Backend
| Pacakges      | Commentaires                                                                                                                  |
| ---           | ---                                                                                                                           |
| controller    | les controller REST (endpoints)                                                                                               |
| model         | <ul><li> les entités </li><li> les autres objets métiers </li><li> les enums </li></ul>                                       |
| persistence   | <ul><li> les converters (map -> json string pour mettre en bdd) </li><li> les DAO </li></ul>                                  |
| properties    | le mapping avec la conf                                                                                                       |
| security      | la couche sécurité                                                                                                            |
| services      | <ul><li> les services des entités (lien entre entité, dao, controller) </li><li> les services des objets métiers </li></ul>   |
| tools         | les constantes                                                                                                                |

## Module Frontend
| Pacakges      | Commentaires                                                                                                                                                                  |
| ---           | ---                                                                                                                                                                           |
| common        | ce qui est indépendant du métier                                                                                                                                              |
| game          | <ul><li> les composants relatifs à l'objet "game" + ces sous composants </li><li> factoring-part : juste un dossier d'organisetion (contient les sous composants) </li></ul>  |
| home-view     | la home page                                                                                                                                                                  |
| interfaces    | <ul><li> les entités </li><li> les autres objets métiers </li><li> les enums </li></ul>                                                                                       |
| params-view   | la vue paramétrage                                                                                                                                                            |
| player        | les composants relatifs à l'objet "player"                                                                                                                                    |
| profile       | les composants relatifs à l'objet "profile"                                                                                                                                   |
| resources     | les resources (call les controllers backend)                                                                                                                                  |
| servives      | les services indépendant (toaster)                                                                                                                                            |
| statistics    | les composants relatifs à l'objet "statistics"                                                                                                                                |
| tools         | les utilitaires (constantes, fonctions...)                                                                                                                                    |
