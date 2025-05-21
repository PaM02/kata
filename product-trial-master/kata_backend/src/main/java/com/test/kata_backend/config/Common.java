package com.test.kata_backend.config;

public class Common {
    public static String emailFromToken = "";
    public static String emailAdmin = "admin@admin.com";

    public static String productCreatedMessage = "Produit créé avec succès";
    public static String accessDeniedMessage = "Accès refusé : seul l'administrateur peut créer des produits";
    public static String productSavedMessage = "Produit enregistré avec succès";
    public static String productNotFoundMessage = "Produit non trouvé";
    public static String productDeletedMessage = "Produit supprimé avec succès";

    public static String invalidEmailMessage = "Adresse e-mail invalide";
    public static String invalidPassword = "Mot de passe invalide";

    public static String message = "message :";
    public static String userString = "utilisateur";
    public static String tokenString = "jeton";
    public static String refreshTokenString = "jeton_de_rafraîchissement";

    public static String usernameExists = "Nom d'utilisateur déjà utilisé";
    public static String emailExists = "Adresse e-mail déjà utilisée";
    public static String userNameCreated = "Utilisateur créé avec succès";

    public static String productExist = "Ce produit est déjà dans votre liste d'envies.";
    public static String productNotExistInCart = "Ce produit n'est pas dans votre panier.";
    public static String productNotExistInWishList = "Ce produit n'est pas dans votre liste d'envies.";
    public static String addCart = "Produit ajouté au panier.";
    public static String addWish = "Produit ajouté à la liste d'envies.";

    public static String UidOrPidNotExist = "L'identifiant utilisateur ou produit n'existe pas.";
    public static String wishListIsEmpty = "Votre liste d'envies est vide.";
    public static String cartItemIsEmpty = "Votre panier est vide.";
}
