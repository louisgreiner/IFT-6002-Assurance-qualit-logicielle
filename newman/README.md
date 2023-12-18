# Utilisation des tests d'API

## Prérequis

- Installer [NodeJS](https://nodejs.org/en/download/)
- Installer [Newman](https://www.npmjs.com/package/newman) avec la commande `npm install -g newman`

## Exécution des tests

- Lancer la commande `newman run Equipe9_API.postman_collection.json` dans le dossier `newman` pour exécuter les tests

## Modification des tests

### Prérequis

- Installer [Postman](https://www.postman.com/downloads/)

### Procédure

- Importer le fichier `Equipe9_API.postman_collection.json` dans Postman
- Modifier les tests dans Postman
- Exporter le fichier `Equipe9_API.postman_collection.json` dans le dossier `newman` pour mettre à jour les tests