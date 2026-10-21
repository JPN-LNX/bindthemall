# bindthemall

Agrégateur RSS / audio / YouTube pour Android, basé sur [AntennaPod](https://github.com/AntennaPod/AntennaPod) (licence GPL-3.0).

## Ajouts par rapport à AntennaPod

- **Écran « Mes médias »** avec trois onglets en haut :
  - **Actus** (journal) : articles des flux RSS sans fichier audio (sites d'actualités, blogs) ;
  - **Audio** (note de musique) : épisodes de podcasts audio ;
  - **Vidéos** (caméra) : épisodes vidéo.

  Tous sont classés du plus récent au plus ancien. C'est le premier écran de la barre du bas et celui qui s'ouvre au démarrage.
- **Catégorie par flux** : chaque flux est classé automatiquement selon ses épisodes (sans fichier → Actus, vidéo → Vidéos, sinon Audio), ou rangé de force dans une catégorie. Le choix se fait à l'import d'un fichier OPML (pour tous les flux importés) ou plus tard dans les réglages du flux, rubrique « Catégorie ».

## Obtenir l'APK

Chaque envoi de code lance une compilation dans l'onglet **Actions** du dépôt. L'APK se télécharge dans l'artefact `antennapod-actus-apk` de la dernière compilation réussie.

L'application s'appelle « Bind Them All » et s'installe à côté d'AntennaPod sans le remplacer.

## Licence

GPL-3.0, comme AntennaPod. Voir `LICENSE`.
