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

# Sprint 1 — 22 février 2025 → 28 février 2025  

## Objectif du sprint  
À la fin du sprint, le modèle du jeu doit fonctionner sans interface Android.  
La grille 6x6 doit être générée correctement sans série initiale et le moteur doit permettre de glisser une ligne ou une colonne avec validation du coup.

EPIC concernés :  
- EPIC 2 — Grille 6x6 et génération initiale  
- EPIC 3 — Glissement et validation des coups  

| US   | Fichiers concernés | Statut | Membre |
|------|--------------------|--------|--------|
| US05 | Grille.java, Case.java | 🟥 | A |
| US06 | TypeElement.java, Generateur.java | 🟥 | Y |
| US07 | Grille.java | 🟥 | A |
| US08 | Grille.java | 🟥 | D |
| US09 | Grille.java | 🟥 | D |
| US10 | MoteurJeu.java | 🟥 | Y |

---

# Sprint 2 — 1 mars 2025 → 7 mars 2025  

## Objectif du sprint  
À la fin du sprint, un coup valide doit déclencher suppression, gravité, remplissage et résolution complète jusqu’à grille stable avec calcul du score.

EPIC concernés :  
- EPIC 4 — Suppression, gravité et résolution  
- EPIC 5 — Score  

| US   | Fichiers concernés | Statut | Membre |
|------|--------------------|--------|--------|
| US11 | Grille.java, Serie.java | 🟥 | A |
| US12 | Grille.java | 🟥 | A |
| US13 | Grille.java | 🟥 | Y |
| US14 | Grille.java, Generateur.java | 🟥 | Y |
| US15 | MoteurJeu.java | 🟥 | D |
| US16 | ScoreManager.java | 🟥 | D |
| US17 | ScoreManager.java | 🟥 | D |
| US18 | MoteurJeu.java | 🟥 | Y |

---

# Sprint 3 — 8 mars 2025 → 14 mars 2025  

## Objectif du sprint  
À la fin du sprint, l’application Android doit afficher le menu principal, permettre de lancer une partie normale et afficher la grille avec score et nombre de coups.

EPIC concernés :  
- EPIC 1 — Menu principal et navigation  
- EPIC 5 — Score  

| US   | Fichiers concernés | Statut | Membre |
|------|--------------------|--------|--------|
| US01 | MenuActivity.java, activity_menu.xml | 🟥 | A |
| US02 | MenuController.java, JeuActivity.java | 🟥 | Y |
| US03 | SeedActivity.java, MenuController.java | 🟥 | D |
| US04 | Activities (gestion back) | 🟥 | A |
| US18 | JeuActivity.java, activity_jeu.xml | 🟥 | Y |

---

# Sprint 4 — 15 mars 2025 → 21 mars 2025  

## Objectif du sprint  
À la fin du sprint, les verrous doivent fonctionner, la fin de partie doit être détectée automatiquement et les interactions doivent être bloquées après la fin.

EPIC concernés :  
- EPIC 6 — Verrous  
- EPIC 7 — Fin de partie  

| US   | Fichiers concernés | Statut | Membre |
|------|--------------------|--------|--------|
| US19 | VerrouManager.java | 🟥 | D |
| US20 | Grille.java | 🟥 | A |
| US21 | VerrouManager.java | 🟥 | D |
| US22 | Grille.java, MoteurJeu.java | 🟥 | Y |
| US23 | JeuController.java | 🟥 | Y |
| US24 | JeuActivity.java | 🟥 | A |

---

# Sprint 5 — 22 mars 2025 → 29 mars 2025  

## Objectif du sprint  
À la fin du sprint, le hard mode, le mode daltonien, la sauvegarde Android et la restauration de partie doivent être fonctionnels. L’application doit être stable et prête pour la livraison.

EPIC concernés :  
- EPIC 8 — Options et accessibilité  
- EPIC 9 — Gestion du cycle de vie Android  

| US   | Fichiers concernés | Statut | Membre |
|------|--------------------|--------|--------|
| US25 | OptionsActivity.java, OptionsController.java | 🟥 | A |
| US26 | NormalRenderer.java, DaltonienRenderer.java | 🟥 | Y |
| US27 | OptionsActivity.java | 🟥 | D |
| US28 | MoteurJeu.java, SharedPreferences | 🟥 | A |
| US29 | JeuActivity.java, sauvegarde état | 🟥 | Y |

---

# Résultat attendu au 29 mars 2025

- Application Android complète
- Respect strict des règles du sujet
- Architecture MVC propre
- Hard mode fonctionnel
- Mode daltonien fonctionnel
- Sauvegarde et restauration opérationnelles
- Code structuré et prêt pour dépôt final