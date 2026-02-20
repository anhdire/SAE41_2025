# Product Backlog — SAE41_2025 — Chuzzle

---

## EPIC 1 — Menu principal et navigation

### US01 – Affichage du menu principal  

En tant que joueur  
Je veux que l’application affiche un menu principal au lancement  
Afin de choisir entre une partie normale, une partie destinée ou les options.  

Le menu est la première activité affichée. Il contient au minimum trois boutons correspondant aux fonctionnalités demandées dans le sujet.

---

### US02 – Lancement d’une partie normale  

En tant que joueur  
Je veux pouvoir démarrer une partie normale  
Afin de jouer immédiatement avec une graine générée automatiquement.  

La graine est générée par le système sans intervention de l’utilisateur. La partie démarre directement dans l’activité de jeu.

---

### US03 – Lancement d’une partie destinée  

En tant que joueur  
Je veux pouvoir saisir une graine personnalisée  
Afin de rejouer exactement la même partie si j’effectue les mêmes actions.  

Une activité intermédiaire permet d’entrer une valeur numérique servant à initialiser le générateur pseudo-aléatoire.

---

### US04 – Navigation avec le bouton retour  

En tant qu’utilisateur  
Je veux que le bouton retour ramène au menu principal  
Afin de respecter le comportement standard Android.  

Depuis les activités Jeu, Options ou Sélection de graine, le retour mène au menu. Depuis le menu, le retour ferme l’application.

---

## EPIC 2 — Grille 6x6 et génération initiale

### US05 – Création d’une grille 6 lignes par 6 colonnes  

En tant que joueur  
Je veux que la grille soit composée de 6 lignes et 6 colonnes  
Afin de respecter les règles du puzzle décrites dans le sujet.  

La grille contient exactement 36 cases organisées en tableau bidimensionnel.

---

### US06 – Attribution aléatoire des 7 types  

En tant que joueur  
Je veux que chaque case reçoive un type choisi aléatoirement parmi 7 possibles  
Afin de garantir la variété du jeu.  

Les types sont répartis de manière pseudo-aléatoire à l’aide d’un générateur initialisé par une graine.

---

### US07 – Absence de série au départ  

En tant que joueur  
Je veux qu’aucune série de trois éléments identiques ne soit présente lors de l’initialisation  
Afin de commencer la partie dans un état stable.  

Il ne doit pas exister plus de deux éléments identiques consécutifs horizontalement ou verticalement.

---

## EPIC 3 — Glissement et validation des coups

### US08 – Glissement circulaire d’une ligne  

En tant que joueur  
Je veux pouvoir faire glisser une ligne de manière circulaire  
Afin de modifier la position des éléments.  

Les éléments qui sortent d’un côté de la grille réapparaissent du côté opposé.

---

### US09 – Glissement circulaire d’une colonne  

En tant que joueur  
Je veux pouvoir faire glisser une colonne de manière circulaire  
Afin de modifier la position des éléments.

---

### US10 – Validation obligatoire d’un coup  

En tant que joueur  
Je veux qu’un coup soit accepté uniquement s’il crée au moins une série de trois éléments identiques  
Afin de respecter les règles du jeu.  

Si le déplacement ne crée aucune série, la grille doit être restaurée dans son état précédent.

---

## EPIC 4 — Suppression, gravité et résolution

### US11 – Détection des séries  

En tant que joueur  
Je veux que toute série d’au moins trois éléments identiques soit détectée  
Afin de pouvoir la supprimer.

---

### US12 – Suppression des séries  

En tant que joueur  
Je veux que les séries détectées soient retirées de la grille  
Afin de libérer des cases.

---

### US13 – Application de la gravité  

En tant que joueur  
Je veux que les éléments situés au-dessus des cases libérées tombent  
Afin de combler les espaces vides.

---

### US14 – Remplissage des cases vides  

En tant que joueur  
Je veux que les cases libérées en haut de chaque colonne soient remplies aléatoirement  
Afin de poursuivre la partie.

---

### US15 – Résolution jusqu’à grille stable  

En tant que joueur  
Je veux que les étapes de suppression, gravité et remplissage soient répétées automatiquement  
Afin d’obtenir une grille stable sans série restante.

---

## EPIC 5 — Score

### US16 – Calcul des points de base  

En tant que joueur  
Je veux gagner 8, 16, 32 ou 64 points selon la longueur d’une série  
Afin de valoriser les combinaisons plus longues.

---

### US17 – Bonus lié aux cascades  

En tant que joueur  
Je veux bénéficier d’un bonus de 50 % par série supplémentaire lors d’un même coup  
Afin de récompenser les enchaînements.

---

### US18 – Affichage du score et du nombre de coups  

En tant que joueur  
Je veux voir le score courant et le nombre de coups joués à l’écran  
Afin de suivre ma progression.

---

## EPIC 6 — Verrous

### US19 – Apparition d’un verrou  

En tant que joueur  
Je veux qu’un élément puisse devenir verrouillé à la fin d’une action  
Afin d’augmenter progressivement la difficulté.

---

### US20 – Blocage des lignes et colonnes  

En tant que joueur  
Je veux qu’un élément verrouillé empêche le glissement de sa ligne et de sa colonne  
Afin de limiter les mouvements possibles.

---

### US21 – Accélération de l’apparition des verrous  

En tant que joueur  
Je veux que la fréquence d’apparition des verrous augmente avec le nombre de coups  
Afin que la difficulté progresse.

---

## EPIC 7 — Fin de partie

### US22 – Détection de l’absence de coup possible  

En tant que joueur  
Je veux que le jeu détecte automatiquement lorsqu’aucun coup n’est possible  
Afin de terminer correctement la partie.

---

### US23 – Blocage des interactions après la fin  

En tant que joueur  
Je veux ne plus pouvoir interagir avec la grille une fois la partie terminée  
Afin de conserver l’état final visible.

---

### US24 – Affichage de la graine en fin de partie  

En tant que joueur  
Je veux voir la graine utilisée pour la partie  
Afin de pouvoir la rejouer ultérieurement.

---

## EPIC 8 — Options et accessibilité

### US25 – Activation d’un hard mode  

En tant que joueur  
Je veux pouvoir activer un hard mode depuis l’écran de configuration  
Afin d’augmenter la difficulté du jeu.

---

### US26 – Différenciation des éléments pour les daltoniens  

En tant que joueur daltonien  
Je veux que les éléments soient distinguables autrement que par la couleur  
Afin de pouvoir jouer sans difficulté.

---

### US27 – Activation d’un mode daltonien  

En tant que joueur  
Je veux pouvoir activer un mode daltonien dans les options  
Afin d’adapter l’affichage du jeu.

---

## EPIC 9 — Gestion du cycle de vie Android

### US28 – Sauvegarde d’une partie en cas de mise en pause  

En tant que joueur  
Je veux que ma partie ne soit pas perdue si l’application est mise en pause  
Afin de reprendre ultérieurement sans perdre ma progression.

---

### US29 – Restauration de l’état de la partie  

En tant que joueur  
Je veux retrouver l’état exact de ma partie après interruption  
Afin de continuer le jeu normalement.