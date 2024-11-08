# Projet-Reseau-Chat

1) Créez un client et un serveur capables de s’échanger des chaînes de caractères.
Vous utiliserez pour cela les sockets TCP.

2) Modifiez votre application de manière à ce que la réception de la chaîne ‘’bye’’
mette fin à la connexion.

3) Lorsqu’un client se connecte, Créez une clé AES, et échangez là.

4) Utilisez cette clé partagée pour chiffrer les messages envoyés et les déchiffrer à la
réception. Affichez, pour chaque message, la version chiffrée et la version en clair.

5) Comment peut-on améliorer la sécurité des échanges ?
Implémentez et commentez votre solution. Vous pouvez utiliser d’autres services de
java. (classes ...)
- commentez votre code et justifiez votre choix (numéro de port, algorithmes, types
de cryptographie …)
- Votre code doit pouvoir s’exécuter sur n’importe quelle machine sans demander de
créer une arborescence de fichiers particulière ou des IP spécifiques.
- Votre rendu consistera en votre code source et un rapport décrivant ou vous en êtes
rendu, les améliorations que vous avez apporté, les ports choisis, etc …

7) Faire une version utilisant les Threads : un serveur gère la communication avec un
nombre quelconque de clients.

8) développer une interface graphique