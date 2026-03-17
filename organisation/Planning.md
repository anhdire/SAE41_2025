# Planning projet - SAE41_2025 - Chuzzle
Periode globale : 22 fevrier 2025 -> 29 mars 2025

---

## Legende

Statut :
🟥 Pas commence
🟨 En cours
🟩 Termine

Membres :
A = Ayoub
Y = Youness
D = Diallo

---

# Sprint 1 - 22 fevrier 2025 -> 28 fevrier 2025

## Objectif du sprint
A la fin du sprint, le modele du jeu doit fonctionner sans interface Android.
La grille 6x6 doit etre generee correctement sans serie initiale et le moteur doit permettre de glisser une ligne ou une colonne avec validation du coup.

EPIC concernes :
- EPIC 2 - Grille 6x6 et generation initiale
- EPIC 3 - Glissement et validation des coups

| US   | Fichiers concernes | Statut | Membre |
|------|--------------------|--------|--------|
| US05 | Grille.java, Case.java | 🟩 | Y |
| US06 | TypeElement.java, Generateur.java | 🟩 | Y |
| US07 | Grille.java | 🟩 | D |
| US08 | Grille.java | 🟩 | A |
| US09 | Grille.java | 🟩 | D |
| US10 | MoteurJeu.java | 🟩 | A |

### Resume des US du sprint 1
- **US05** : Creation de la structure de la grille 6x6.
- **US06** : Mise en place des 7 types d'elements et du generateur aleatoire.
- **US07** : Verification qu'aucune serie initiale n'est presente.
- **US08** : Implementation du glissement circulaire des lignes.
- **US09** : Implementation du glissement circulaire des colonnes.
- **US10** : Validation d'un coup uniquement s'il cree une serie.

---

# Sprint 2 - 1 mars 2025 -> 7 mars 2025

## Objectif du sprint
A la fin du sprint, un coup valide doit declencher suppression, gravite, remplissage et resolution complete jusqu'a grille stable avec calcul du score.

EPIC concernes :
- EPIC 4 - Suppression, gravite et resolution
- EPIC 5 - Score

| US   | Fichiers concernes | Statut | Membre |
|------|--------------------|--------|--------|
| US11 | Grille.java, Serie.java | 🟩 | D |
| US12 | Grille.java | 🟩 | A |
| US13 | Grille.java | 🟩 | D |
| US14 | Grille.java, Generateur.java | 🟩 | D |
| US15 | MoteurJeu.java | 🟩 | A |
| US16 | ScoreManager.java | 🟩 | Y |
| US17 | ScoreManager.java | 🟩 | A |
| US18 | MoteurJeu.java | 🟩 | Y |

### Resume des US du sprint 2
- **US11** : Detection des series horizontales et verticales.
- **US12** : Suppression des series detectees.
- **US13** : Application de la gravite.
- **US14** : Remplissage des cases vides.
- **US15** : Resolution complete jusqu'a stabilisation.
- **US16** : Calcul des points de base.
- **US17** : Application du bonus de cascade.
- **US18** : Mise a jour du score et du nombre de coups.

---

# Sprint 3 - 8 mars 2025 -> 14 mars 2025

## Objectif du sprint
A la fin du sprint, l'application Android doit afficher le menu principal, permettre de lancer une partie normale et afficher la grille avec score et nombre de coups.

EPIC concernes :
- EPIC 1 - Menu principal et navigation
- EPIC 5 - Score

| US   | Fichiers concernes | Statut | Membre |
|------|--------------------|--------|--------|
| US#1 | resource xml | 🟩 | D |
| US01 | MenuActivity.java, activity_menu.xml | 🟩 | D |
| US02 | MenuController.java, JeuActivity.java, activity_jeu.xml | 🟩 | D |
| US03 | SeedActivity.java, activity_seed.xml | 🟩 | A |
| US04 | MenuActivity.java, JeuActivity.java | 🟩 | A |
| US18 | JeuActivity.java, activity_jeu.xml | 🟩 | Y |
| US19 | VerrouManager.java | 🟩 | D |
| US20 | Grille.java | 🟩 | Y |
| US21 | VerrouManager.java | 🟩 | D |

### Resume des US du sprint 3
- **US#1** : Creation de la palette de couleur et des resources de base.
- **US01** : Creation de l'ecran de menu principal.
- **US02** : Lancement d'une partie normale.
- **US03** : Saisie et transmission de la graine.
- **US04** : Gestion du bouton retour.
- **US18** : Affichage du score et du nombre de coups.
- **US19** : Apparition des verrous.
- **US20** : Blocage des lignes et colonnes.
- **US21** : Acceleration progressive des verrous.

---

# Sprint 4 - 15 mars 2025 -> 21 mars 2025

## Objectif du sprint
A la fin du sprint, les verrous doivent fonctionner, la fin de partie doit etre detectee automatiquement et les interactions doivent etre bloquees apres la fin.

EPIC concernes :
- EPIC 6 - Verrous
- EPIC 7 - Fin de partie

| US   | Fichiers concernes | Statut | Membre |
|------|--------------------|--------|--------|
| US22 | Grille.java, MoteurJeu.java | 🟩 | D      |
| US23 | JeuController.java, JeuActivity.java | 🟥 | A      |
| US24 | JeuActivity.java, activity_jeu.xml | 🟨 | Y      |
| US25 | OptionsActivity.java, OptionsController.java, activity_options.xml | 🟨 | D      |
| US26 | NormalRenderer.java, DaltonienRenderer.java | 🟨 | Y      |
| US27 | OptionsActivity.java, activity_options.xml | 🟥 | A      |
| US28 | MoteurJeu.java, SharedPreferences | 🟨 | D      |
| US29 | JeuActivity.java, activity_jeu.xml | 🟥 | A      |

### Resume des US du sprint 4
- **US22** : Detection de fin de partie.
- **US23** : Desactivation des interactions apres la fin.
- **US24** : Affichage de la graine en fin de partie.
- **US25** : Activation du hard mode.
- **US26** : Implementation de l'affichage daltonien.
- **US27** : Ajout du bouton mode daltonien.
- **US28** : Sauvegarde automatique de la partie.
- **US29** : Restauration complete de l'etat du jeu.

---

# Sprint 5 - 22 mars 2025 -> 29 mars 2025

## Objectif du sprint
A la fin du sprint, le hard mode, le mode daltonien, la sauvegarde Android et la restauration de partie doivent etre fonctionnels. L'application doit etre stable et prete pour la livraison.

EPIC concernes :
- EPIC 8 - Options et accessibilite
- EPIC 9 - Gestion du cycle de vie Android

| US   | Fichiers concernes | Statut | Membre |
|------|--------------------|--------|--------|

### Resume des US du sprint 5

---
