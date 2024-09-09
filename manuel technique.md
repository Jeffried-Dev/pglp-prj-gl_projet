# MANUEL TECHNIQUE


> Le programme a été réaliser par le biais de trois classes qui sont 
 - Commande : 
 - Dossier : , 
 - Fichier : ,
 - Repertoire :  
 - APP : fonction principal qui permet de relier toutes les classes.
 
La classe GESTIONNAIRE contient les codes sources de l'ensemble de toutes les fonctions du gestionnaire de fichier il Gère les données, la logique et les règles de l'application. Il représente la structure des données et répond aux requêtes effectuées par la classe App. Dans un modelé MVC cette classe est considéré comme notre Model.
la classe COMMANDEGESTION  gère tous ce qui est en rapport avec le terminal et l'utilisateur, elle gère en fait les entrées/sorti en rapport avec l'utilisateur plus précisément, cette classe récupère les commandes de l'utilisateur les faits comparaitre à une expression régulière définis au préalable et retourne une commande qui respecte l'expression régulière en question et envoi la commande en question a la classe APP pour exécution. Aussi cette classe permet de vérifier les paramètres entrés par l’utilisateur (répertoire courant et répertoire racine) et vérifie si ceux-ci sont vraiment des répertoires pour ne pas travailler sur un fichier comme étant des répertoires courants.  Aussi la classe permet de créer un terminal JLine et un affichage des titres en utilisant la bibliothèque FigletFont du GitHub spécifier par le professeur. En gros cette classe est notre Vue dans le modèle MVC.
La classe APP permet d'exécuter les commandes de l'utilisateur proprement dit. En gros notre classe reçoit la commande de l'utilisateur depuis la classe COMMANDEINTERFACE analyse la commande et appelle la fonction correspondante a la commande dans la classe GESTIONNAIRE l’exécute et renvoi le résultat a la classe COMMANDEINTERFACE pour affichage sure le terminal.

Les traitements réalisés pour gérer une commande saisie par l’utilisateur sont : une fois que l'utilisateur saisit une commande celle-ci est traite au niveau de la classe COMMANDEINTERFACE pour vérifier si elle respecte l'expression régulière initialement définit pour la syntaxe de toutes les commandes du gestionnaire de fichier.
Si la commande respecte la ReGex alors la commande est envoyer dans la classe APP pour exécution sinon un message d'erreur de syntaxe lui est envoyer lui demandant de consulter l'aide associer au gestionnaire de fichier en tapant la commande ? et une fois l'aide consulter un prompt est afficher en lui demandant d'entrer à nouveau une commande.
La ReGex donne en quelque sorte toute les syntaxes valide d'une commande du gestionnaire de fichier. Aussi lorsque l'utilisateur entre son répertoire courant de travail ce répertoire en question est aussi analyser pour savoir si il s'agit bien évidement d'un répertoire et non d'un fichier si ce n'est pas le cas alors un message est afficher lui demandant d'entrer un dossier courant valide avant de continuer.
EXEMPLE DE COMMANDE CORRECTE : 4 visu
			       4 copy
				find App.java
				4 + "une annotation"

EXEMPLE DE COMMANDE INCORRECTE : 4visu (il faut un espace entre le 4 et le visu)
				 4 cop (nom commande mal saisie)
				find App (il faut l'extension du fichier pour une recherche plus optimal)
				4 + une annotation (il faut les cotes " " )
Mais pour éviter tout ceci l'utilisateur devra consulter l'aide ou le manuel d'utilisation car c'est très bien détaillé a l'intérieure.

Les bibliothèques qui ont été utilise sont entre autre la bibliothèque
JLINE.TERMINAL : JLine est une bibliothèque Java qui facilite la gestion des entrées/sorties de la console. Elle offre des fonctionnalités telles que la lecture de lignes, l'édition de ligne avec la possibilité de déplacer le curseur, la complétion automatique, et d'autres fonctionnalités liées à l'interaction avec l'utilisateur dans un environnement de console.
jfiglet.FigletFont : la bibliothèque Java Figlet. Figlet est un programme qui génère du texte stylisé sous forme de bannières en utilisant différentes polices ASCII. 
regex.Pattern : la bibiotheque regex.Pattern gere tout ce qui est en rapport avec les expressions reguliere 
nio.file.Path : la bibiotheque nio.file.Path pour la manipulation des chemins absolu/relatif
