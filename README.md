

---

# Système de Suivi de Projets et de Facturation (Full-Stack)

## 📌 Context
Ce projet a été développé dans le cadre de la licence **Systèmes d’Information Répartis (SIR)** à la **Faculté des Sciences et Techniques (FST) de Marrakech**. Il s'agit d'une solution complète de gestion du cycle de vie des projets, allant de l'affectation des collaborateurs jusqu'à l'encaissement des factures.

## 🎯 Objectif & Problématique
**Objectif :** Automatiser et centraliser le suivi des projets techniques pour garantir une visibilité en temps réel sur l'avancement des travaux et la santé financière de l'entreprise.

**Problématique :** * Manque de synchronisation entre l'achèvement des tâches et l'émission des factures.
* Difficulté de gestion et de centralisation des livrables et documents techniques.
* Absence d'indicateurs de performance (KPI) consolidés pour la direction.

---

## 📖 Table des matières
1.  [Rôles Utilisateurs](#-rôles-utilisateurs)
2.  [Fonctionnalités Principales](#-fonctionnalités-principales)
3.  [Architecture du Projet](#-architecture-du-projet)
4.  [Technologies Utilisées](#-technologies-utilisées)
5.  [Modèle de Données](#-modèle-de-données)
6.  [Endpoints API Clés](#-endpoints-api-clés)

---

## 👥 Rôles Utilisateurs
L'application implémente une sécurité basée sur les rôles (RBAC) :
* **Administrateur :** Gestion totale des clients, employés et configuration système.
* **Directeur :** Accès au tableau de bord, reporting global et suivi des projets.
* **Comptable :** Gestion exclusive du module de facturation et suivi des paiements.

---

## ✨ Fonctionnalités Principales
* **Gestion de Projets & Phases :** Découpage des projets en phases avec suivi des dates, budgets et états de réalisation.
* **Gestion Documentaire & Livrables :** Upload et download sécurisés de fichiers techniques rattachés aux phases ou aux projets.
* **Workflow de Facturation :** Flux métier strict : Phase terminée → Génération de facture → Validation du paiement.
* **Dashboard Dynamique :** Visualisation en temps réel du Chiffre d'Affaires, des projets actifs et de la répartition des revenus par projet via des graphiques.

---

## 🏗️ Architecture du Projet
Le projet adopte une structure **Monorepo** pour faciliter le déploiement et la maintenance :
* **/backend :** API REST développée avec Spring Boot (Architecture en couches : Controller, Service, Repository, DTO, Mapper).
* **/frontend :** Interface SPA moderne développée avec React et Tailwind CSS.

---

## 💻 Technologies Utilisées

### Backend
* **Java 17** & **Spring Boot 3**
* **Spring Data JPA** (Persistance des données)
* **Spring Security** (Authentification & Autorisation)
* **MapStruct** (Mapping DTO/Entity)
* **MySQL** (Base de données relationnelle)

### Frontend
* **React 18** (Vite.js)
* **Tailwind CSS** (Design UI)
* **Axios** (Communications API)
* **Recharts** (Visualisation de données)
* **Lucide React** (Bibliothèque d'icônes)

---

## 🗄️ Tables de Données
Le schéma relationnel repose sur les entités suivantes :
* **Organisme :** Clients commanditaires des projets.
* **Employe :** Collaborateurs affectés aux phases.
* **Projet :** Entité parente contenant le budget global et les documents.
* **Phase :** Unité de travail (Conception, Développement, etc.).
* **Livrable / Document :** Fichiers physiques stockés et rattachés.
* **Facture :** Document comptable lié à une phase terminée.

---

## 📡 API Endpoints (Extraits)


## 📑 1. Gestion des Documents (Projet)
Ces routes permettent de gérer les fichiers techniques rattachés directement à un projet.

* **`POST /api/projets/{projetId}/documents`** : Ajouter un nouveau document (MultipartFile + DTO).
* **`GET /api/projets/{projetId}/documents`** : Lister tous les documents d'un projet spécifique.
* **`GET /api/documents/{id}`** : Consulter les détails d'un document.
* **`PUT /api/documents/{id}`** : Modifier les informations ou le fichier d'un document.
* **`DELETE /api/documents/{id}`** : Supprimer un document et son fichier physique.
* **`GET /api/documents/{id}/download`** : Télécharger le fichier physique.

---

## ⚙️ 2. Gestion des Phases
Ce module gère le cycle de vie des phases de développement.

* **`GET /api/projets/{projetId}/phases`** : Récupérer toutes les phases liées à un projet.
* **`PUT /api/phases/{id}`** : Mettre à jour les informations d'une phase.
* **`PUT /api/phases/{id}/terminer`** : Point d'entrée dédié pour marquer une phase comme "Terminée" (`etatRealisation = true`).

---

## 💰 3. Facturation & Paiement
Module utilisé principalement par le rôle **Comptable** pour transformer le travail réalisé en revenus.

* **`GET /api/factures`** : Liste complète de toutes les factures émises.
* **`GET /api/factures/facturables`** : Récupérer les phases qui sont terminées mais pas encore facturées.
* **`POST /api/factures`** : Créer une nouvelle facture pour une phase spécifique.
* **`DELETE /api/factures/{id}`** : Supprimer une facture et libérer la phase associée.
* **`PUT /api/factures/{id}/payer`** : Encaisser une facture et marquer la phase comme "Payée" (`etatPaiement = true`).

---

## 📊 4. Reporting & Tableau de Bord
Endpoints optimisés pour la data-visualisation sur le dashboard.

* **`GET /api/reporting/chiffre-affaire`** : Retourne le montant total des factures encaissées.
* **`GET /api/reporting/par-projet`** : Répartition du chiffre d'affaires par nom de projet.
* **`GET /api/reporting/phases-terminees`** : Nombre total de phases achevées dans le système.
* **`GET /api/reporting/projets-en-cours`** : Nombre total de projets actifs.
* **`GET /api/reporting/a-facturer`** : Nombre de phases terminées en attente de facturation.

---
FRONTEND CODE SOURCE : https://github.com/marouane1zahran/PROJET_SPRINGBOOT---SYSTEME-DE-SUIVI-DE-PROJETS-FRONTEND.git

DEMO 


https://github.com/user-attachments/assets/25e81ad5-1501-4493-b452-40337f27f416


---
*Projet réalisé par Marouane Zahran , abderrahmane SOUAIKI ,oussama el mouekken- SIR 2026*.
