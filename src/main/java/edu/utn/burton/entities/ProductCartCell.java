package edu.utn.burton.entities;

import edu.utn.burton.Burton;
import edu.utn.burton.controller.CartController;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;



public class ProductCartCell extends ListCell<ProductUser> {
    private ProductUser currentProduct;
    private CartController ctrlCart;
    SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);
    private ImageView imageView = new ImageView();
    private Label titleLabel = new Label();
    private Label quantityLabel = new Label();
    private Label unityPriceLabel = new Label();
    private Label totalAmountLabel = new Label();
    private MFXButton eliminar = new MFXButton();
    private MFXButton editar = new MFXButton();
    private ImageView shopingcartIcon = new ImageView();
    private Spinner<Integer> amount = new Spinner<>();
    private HBox content = new HBox(20);
    

    public ProductCartCell() {
        ctrlCart = new CartController();
        
        
        
        amount.setValueFactory(valueFactory);
        
        eliminar.setOnAction(ev -> {
        ctrlCart.deleteProducto(currentProduct);
        System.out.println(Cart.getInstance().getProducts().toString());
        });
        
        editar.setOnAction(ev -> {
        ctrlCart.addProduct(currentProduct);
        System.out.println(Cart.getInstance().getProducts().toString());
        });
       
        // Configurar el tamaño de la imagen
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);
        imageView.setPreserveRatio(true);
        
        // Configurar los anchos preferidos de los labels
        titleLabel.setPrefWidth(200);
        quantityLabel.setPrefWidth(100);
        unityPriceLabel.setPrefWidth(150);
        totalAmountLabel.setPrefWidth(100);

        // Configurar estilos
        eliminar.getStyleClass().add("product-botoEliminar");
        imageView.getStyleClass().add("product-image");
        titleLabel.getStyleClass().add("product-title");
        quantityLabel.getStyleClass().add("product-quantity");
        unityPriceLabel.getStyleClass().add("product-price");
        totalAmountLabel.getStyleClass().add("product-total");
        

        // apply images xd
        
        shopingcartIcon.setImage(new Image("/assets/basura.png"));
        shopingcartIcon.setPreserveRatio(true); 
        shopingcartIcon.setFitWidth(12); 
        eliminar.setGraphic(shopingcartIcon);
        
        // Configurar el HBox
        content.setAlignment(Pos.CENTER_LEFT);
        content.setPadding(new Insets(10));
        content.getChildren().addAll(imageView, titleLabel, quantityLabel, unityPriceLabel, totalAmountLabel, eliminar, amount, editar);
        content.getStyleClass().add("product-cart-cell");
        
        content.getStylesheets().add(Burton.class.getResource("/styles/cartmenu.css").toExternalForm());
        
        // Configurar el gráfico de la celda
        setGraphic(content);
    }

    @Override
    public void updateItem(ProductUser product, boolean empty) {
        super.updateItem(product, empty);
        
        if (empty || product == null) {
            setGraphic(null);
            currentProduct = null;
        } else {
            currentProduct = product;
            String title = product.getNameProduct();
            if (title.length() > 27) {
                title = title.substring(0, 26) + "...";
            }
            
            titleLabel.setText(title);
            quantityLabel.setText("Cantidad: " + product.getQuantity());
            unityPriceLabel.setText("Precio unitario: $" + product.getUnitePrice());
            totalAmountLabel.setText("Total: $" + product.getSubtotal());
           
            
            try {
                imageView.setImage(new Image(product.getImagePrincipal()));
            } catch (Exception e) {
                imageView.setImage(new Image(Burton.class.getResource("/assets/unknown.png").toString()));
            }
            
            setGraphic(content);
        }
    }
}