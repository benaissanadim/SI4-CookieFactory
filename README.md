# Bienvenue dans notre Cookie Factory !

# Cookiefactory-22-23-H
Projet realisé par : 
- Benaissa Nadim 
- Boubia Marouane
- Saissi Omar
- Zoubair Hamza
- Elgarmit Youssef

## doc
Contient le rapport final

## .github
   1. Contient sous workflows/maven.yml, une version d'un fichier d'actions qui est déclenché dès que vous poussez du code. 
Sur cette version initiale seule un test Junit5 est déclenché pour vérifier que tout fonctionne.
       - Github Actions (See in .github/workflows) to simply make a maven+test compilation
  2. Contient sous ISSUE_TEMPLATE, les modèles pour les issues user_story et bug. Vous pouvez le compléter à votre guise.

## src
 - pom.xml : 
       - Cucumber 7 et junit 5
       - Maven compatible
       - JDK 17



## User stories 

## Epic 1 : Commander un cookie

[US1] choose cookie to order #1 => MILESTONE 1 : Sprint 1
En tant que client , je veux choisir une cookie afin de la commander

[US2] choose store  #2 => MILESTONE 1 : Sprint 1
En tant que client, je veux choisir un magasin afin que la commande soit affectée à un magasin.

[US3] pay order  #3 => MILESTONE 2 : Sprint 2
En tant que client, je veux payer une commande afin que ma commande soit prête à être cuisinée.

[US6] Add Cookies To Basket with quantity #7 => MILESTONE 2 : Sprint 2
En tant que client/visiteur, je veux choisir les Cookie et la quantité souhaitée afin qu'ils soient ajoutés au panier.

[US7] Validate the withdrawal of an order #8 => MILESTONE 2 : Sprint 2
En tant que caissier, je veux valider le retrait de la commande afin que la commande soit prise.

[US8] Validate the basket #9  => MILESTONE 2 : Sprint 2
En tant que client/visiteur, je veux valider le panier afin que la commande soit créée avec les cookies du panier.

[US11] get total price order #12 => MILESTONE 3 : Sprint 3
En tant que client, je veux obtenir le prix total de la commande avec les taxes du magasin afin de pouvoir payer.

[US12] Choose time to pick up the order #13 => MILESTONE 3 : Sprint 3
En tant que client, je veux choisir l'heure à laquelle je dois passer ma commande afin qu'elle soit prête à temps.


## Epic 2 : Commander un cookie personnalisé

[US24] create personalized cookies #31 => MILESTONE 5 : Sprint 5
En tant que client, je veux créer des cookies personnalisés afin de  pouvoir les commander.

[US25]  get total price of personalized cookie #34 => MILESTONE 5 : Sprint 5
En tant que client, je veux  obtenir le prix total du cookie personnalisé afin de pouvoir payer .


## Epic 3 : Actions des clients

[US14] Join Loyalty Program #15 => MILESTONE 3 : Sprint 3
En tant que client, je veux adhérer au loyalty programme afin de bénéficier d'une réduction de 10 % sur ma prochaine commande.

[US15] Display order history #16 => MILESTONE 3 : Sprint 3
En tant que client, je veux connaître les commandes passées afin que je puisse avoir un historique de mes commandes.

[US16] Cancel Order #17 => MILESTONE 4 : Sprint 4
En tant que client, je veux annuler ma commande afin que la commande soit annulée.

[US21] Eligibility to Order #27 => MILESTONE 4 : Sprint 4
En tant que client, je veux savoir si je peux commander un cookie afin de passer une commande

[US18] User Notification #19 => MILESTONE 4 : Sprint 4
En tant que client, je veux être informé lorsque ma commande est prête afin de pouvoir la récupérer.


## Epic 4 : Gestion du magasin

[US10] manage store scheduler #11 => MILESTONE 3 : Sprint 3
En tant que directeur de magasin, je veux gérer le planificateur de magasin afin que le magasin ait une heure d'ouverture et de fermeture.

[US13] add stock ingredients #14 => MILESTONE 3 : Sprint 3
En tant que directeur de magasin, je veux ajouter des ingrédients de stock lorsque le stock est inférieur à un seuil afin que le stock augmente.


## Epic 5 : Gestion du magasin avec cookie personnalisé

[US26] Special Store #35 => MILESTONE 5 : Sprint 5
En tant que manager, je veux avoir un magasin spécial afin que les utilisateurs puissent commander des cookies personnalisés.


## Epic 6 : Too Good To Go

[US22] create surprise cart - Too Good To Go #29 => MILESTONE 5 : Sprint 5
En tant que manager, je veux que les commandes obsolètes deviennent des paniers surprises afin que je les propose à l'application.

[US23] subscribe to be notified of surprise carts ToGoodToGo #30 => MILESTONE 5 : Sprint 5
En tant que client, je veux m'abonner aux paniers surprise afin d'être informé de l'arrivée d'un nouveau panier surprise.


## Epic 7 : Actions des cuisiniers

[US4] Cook Validation of the Beginning of Preparation  #4 => MILESTONE 1 : Sprint 1
En tant que cuisinier, je veux valider le début de la préparation de la commande afin de
commencer la préparation de la commande.

[US5] Cook Validation of the  end of Preparation #6 => MILESTONE 1 : Sprint 1
En tant que cuisinier, je veux valider la fin de la préparation de la commande afin que celle-ci soit prête à être récupérée.

[US17] cook scheduler #18 => MILESTONE 4 : Sprint 4
En tant que cuisinier, je veux disposer d'un planificateur de temps afin de pouvoir organiser les commandes attribuées .


## Epic 8 : Actions des managers

[US20] obsolete order #22 => MILESTONE 4 : Sprint 4
En tant que directeur de magasin, je veux changer le statut d'une commande qui n'a pas été récupéré 2 heures après l'heure de retrait afin que la commande soit considérée comme obsolète

[US9] create new recipe cookie #10 => MILESTONE 3 : Sprint 3
En tant que chef cuisinier, je veux ajouter de nouvelles recettes de Cookie dans le catalogue afin que les utilisateurs puissent choisir les Cookie à commander.

[US19] validate cookie to add in catalog #21 => MILESTONE 4 : Sprint 4
En tant que gestionnaire de marque, je veux valider le cookie  afin que le Cookie soit ajouté au catalogue.

   
   
