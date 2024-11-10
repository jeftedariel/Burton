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
import javafx.scene.layout.VBox;

/**
 *
 * @author Justin Rodriguez Gonzalez
 */

public class ProductCartCell extends ListCell<ProductCart> {

    private ProductCart currentProduct;
    private CartController ctrlCart;
    SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);
    private ImageView imageView = new ImageView();
    private Label titleLabel = new Label();
    private Label quantityLabel = new Label();
    private Label unityPriceLabel = new Label();
    private Label totalAmountLabel = new Label();
    private MFXButton delete = new MFXButton();
    private MFXButton edit = new MFXButton();
    private ImageView deleteimage = new ImageView();
    private ImageView addimage = new ImageView();
    private Spinner<Integer> amount = new Spinner<>();
    private HBox content = new HBox(20);
    private VBox buttonBox = new VBox(5);

    public ProductCartCell() {

        ctrlCart = new CartController();

        amount.setValueFactory(valueFactory);

        delete.setOnAction(ev -> {
            int valueSpinner = amount.getValue();
            ctrlCart.deleteProducto(currentProduct, valueSpinner);
        });

        edit.setOnAction(ev -> {
            int valueSpinner = amount.getValue();
            ctrlCart.addProduct(currentProduct, valueSpinner);
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
        delete.getStyleClass().add("product-botonDelete");
        edit.getStyleClass().add("product-botonAdd");
        imageView.getStyleClass().add("product-image");
        titleLabel.getStyleClass().add("product-title");
        quantityLabel.getStyleClass().add("product-quantity");
        unityPriceLabel.getStyleClass().add("product-price");
        totalAmountLabel.getStyleClass().add("product-total");

        deleteimage.setImage(new Image("/assets/basura.png"));
        deleteimage.setPreserveRatio(true);
        deleteimage.setFitWidth(12);
        delete.setGraphic(deleteimage);

        addimage.setImage(new Image("/assets/add.png"));
        addimage.setPreserveRatio(true);
        addimage.setFitWidth(12);
        edit.setGraphic(addimage);

        // Configurar el VBox para los botones
        buttonBox.getChildren().addAll(delete, edit);
        buttonBox.setAlignment(Pos.CENTER); // Alinear los botones en el centro

        // Añadir el VBox al HBox de contenido
      

        // Configurar el HBox
        content.setAlignment(Pos.CENTER_LEFT);
        content.setPadding(new Insets(10));
        content.getChildren().addAll(imageView, titleLabel, quantityLabel, unityPriceLabel, totalAmountLabel, amount);
        content.getStyleClass().add("product-cart-cell");

        content.getStylesheets().add(Burton.class.getResource("/styles/cartmenu.css").toExternalForm());
        content.getChildren().add(buttonBox);
        // Configurar el gráfico de la celda
        setGraphic(content);
    }

    @Override
    public void updateItem(ProductCart product, boolean empty) {
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
