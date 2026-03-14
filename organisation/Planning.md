# Planning projet — SAE41_2025 — Chuzzle
Période globale : 22 février 2025 → 29 mars 2025

---

## Légende

Statut :  
🟥 Pas commencé  
🟨 En cours  
🟩 Terminé

Membres :  
A = Ayoub  
Y = Youness  
D = Diallo

---

# Sprint 1 — 22 février 2025 -> 28 février 2025

## Objectif du sprint
À la fin du sprint, le modèle du jeu doit fonctionner sans interface Android.  
La grille 6x6 doit être générée correctement sans série initiale et le moteur doit permettre de glisser une ligne ou une colonne avec validation du coup.

EPIC concernés :
- EPIC 2 — Grille 6x6 et génération initiale
- EPIC 3 — Glissement et validation des coups

| US   | Fichiers concernés | Statut | Membre |
|------|--------------------|--------|--------|
| US05 | Grille.java, Case.java | 🟩 | Y |
| US06 | TypeElement.java, Generateur.java | 🟩 | Y |
| US07 | Grille.java | 🟩 | D |
| US08 | Grille.java | 🟩 | A |
| US09 | Grille.java | 🟩 | D |
| US10 | MoteurJeu.java | 🟩 | A |

### Résumé des US du sprint 1
- **US05** : Création de la structure de la grille 6x6.
- **US06** : Mise en place des 7 types d’éléments et du générateur aléatoire.
- **US07** : Vérification qu’aucune série initiale n’est présente.
- **US08** : Implémentation du glissement circulaire des lignes.
- **US09** : Implémentation du glissement circulaire des colonnes.
- **US10** : Validation d’un coup uniquement s’il crée une série.

---

# Sprint 2 — 1 mars 2025 -> 7 mars 2025

## Objectif du sprint
À la fin du sprint, un coup valide doit déclencher suppression, gravité, remplissage et résolution complète jusqu’à grille stable avec calcul du score.

EPIC concernés :
- EPIC 4 — Suppression, gravité et résolution
- EPIC 5 — Score

| US   | Fichiers concernés | Statut | Membre |
|------|--------------------|--------|--------|
| US11 | Grille.java, Serie.java | 🟩 | D |
| US12 | Grille.java | 🟩 | A |
| US13 | Grille.java | 🟩 | D |
| US14 | Grille.java, Generateur.java | 🟩 | D |
| US15 | MoteurJeu.java | 🟩 | A |
| US16 | ScoreManager.java | 🟩 | Y |
| US17 | ScoreManager.java | 🟩 | A |
| US18 | MoteurJeu.java | 🟩 | Y |

### Résumé des US du sprint 2
- **US11** : Détection des séries horizontales et verticales.
- **US12** : Suppression des séries détectées.
- **US13** : Application de la gravité.
- **US14** : Remplissage des cases vides.
- **US15** : Résolution complète jusqu’à stabilisation.
- **US16** : Calcul des points de base.
- **US17** : Application du bonus de cascade.
- **US18** : Mise à jour du score et du nombre de coups.

---

# Sprint 3 — 8 mars 2025 -> 14 mars 2025

## Objectif du sprint
À la fin du sprint, l’application Android doit afficher le menu principal, permettre de lancer une partie normale et afficher la grille avec score et nombre de coups.

EPIC concernés :
- EPIC 1 — Menu principal et navigation
- EPIC 5 — Score

| US   | Fichiers concernés | Statut | Membre |
|------|--------------------|--------|--------|
| US#1 | resoucre xml | 🟩 | D |
| US01 | MenuActivity.java, activity_menu.xml | 🟩 | D |
| US02 | MenuController.java, JeuActivity.java, activity_jeu.xml | 🟨 | D |
| US03 | SeedActivity.java, activity_seed.xml | 🟨 | A |
| US04 | MenuActivity.java, JeuActivity.java | 🟥 | A |
| US18 | JeuActivity.java, activity_jeu.xml | 🟥 | Y |
| US19 | VerrouManager.java | 🟥 | D |
| US20 | Grille.java | 🟥 | Y |
| US21 | VerrouManager.java | 🟥 | D |

### Résumé des US du sprint 3
- **US#1** : Création de la pallete de couleur et de toute les resource de base.
- **US01** : Création de l’écran de menu principal.
- **US02** : Lancement d’une partie normale.
- **US03** : Saisie et transmission de la graine.
- **US04** : Gestion du bouton retour.
- **US18** : Affichage du score et du nombre de coups.
- **US19** : Apparition des verrous.
- **US20** : Blocage des lignes et colonnes.
- **US21** : Accélération progressive des verrous.
---

# Sprint 4 — 15 mars 2025 -> 21 mars 2025

## Objectif du sprint
À la fin du sprint, les verrous doivent fonctionner, la fin de partie doit être détectée automatiquement et les interactions doivent être bloquées après la fin.

EPIC concernés :
- EPIC 6 — Verrous
- EPIC 7 — Fin de partie

| US   | Fichiers concernés | Statut | Membre |
|------|--------------------|--------|--------|
| US22 | Grille.java, MoteurJeu.java | 🟥 | D |
| US23 | JeuController.java, JeuActivity.java | 🟥 | A |
| US24 | JeuActivity.java, activity_jeu.xml | 🟥 | A |
| US25 | OptionsActivity.java, OptionsController.java, activity_options.xml | 🟥 | D |
| US26 | NormalRenderer.java, DaltonienRenderer.java | 🟥 | A |
| US27 | OptionsActivity.java, activity_options.xml | 🟥 | Y |
| US28 | MoteurJeu.java, SharedPreferences | 🟥 | D |
| US29 | JeuActivity.java, activity_jeu.xml | 🟥 | A |

### Résumé des US du sprint 4

- **US22** : Détection de fin de partie.
- **US23** : Désactivation des interactions après la fin.
- **US24** : Affichage de la graine en fin de partie.
- **US25** : Activation du hard mode.
- **US26** : Implémentation de l’affichage daltonien.
- **US27** : Ajout du bouton mode daltonien.
- **US28** : Sauvegarde automatique de la partie.
- **US29** : Restauration complète de l’état du jeu.
---

# Sprint 5 — 22 mars 2025 -> 29 mars 2025

## Objectif du sprint
À la fin du sprint, le hard mode, le mode daltonien, la sauvegarde Android et la restauration de partie doivent être fonctionnels. L’application doit être stable et prête pour la livraison.

EPIC concernés :
- EPIC 8 — Options et accessibilité
- EPIC 9 — Gestion du cycle de vie Android

| US   | Fichiers concernés | Statut | Membre |
|------|--------------------|--------|--------|


### Résumé des US du sprint 5


---