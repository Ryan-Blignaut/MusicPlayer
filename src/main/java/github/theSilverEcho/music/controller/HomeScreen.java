package github.theSilverEcho.music.controller;

import com.jfoenix.controls.JFXToggleButton;
import github.theSilverEcho.music.config.Category;
import github.theSilverEcho.music.config.Config;
import github.theSilverEcho.music.config.selector.BooleanSelector;
import github.theSilverEcho.music.config.selector.CategorySelector;
import github.theSilverEcho.music.config.selector.MenuSelector;
import github.theSilverEcho.music.config.selector.SliderSelector;
import github.theSilverEcho.music.player.MainMusicUi;
import github.theSilverEcho.music.util.ReflectionHelper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class HomeScreen
{
	private final HashMap<Class<? extends Annotation>, BiConsumer<Field, VBox>> map = new HashMap<>();

	{
		map.put(MenuSelector.class, this::addMenuSelector);
		map.put(SliderSelector.class, this::addSliderSelector);
		map.put(BooleanSelector.class, this::addBooleanSelector);
	}


	@FXML
	void closeScreen(ActionEvent event)
	{
		MainMusicUi.stage.close();
	}

	@FXML
	private TabPane tabPane;


	final EnumMap<Category, VBox> categoryVBoxMap = new EnumMap<>(Category.class);

	@FXML
	void initialize()
	{
		Config.getAllFields().forEach(this::genSettings);
	}


	private void genSettings(Field field)
	{
		map.forEach((clazz, fieldConsumer) ->
		{
			if (field.isAnnotationPresent(clazz))
			{
				final Category category = field.isAnnotationPresent(CategorySelector.class) ?
						field.getAnnotation(CategorySelector.class).category() : Category.GENERAL;
				if (!categoryVBoxMap.containsKey(category))
				{
					final VBox vBox = new VBox();
					vBox.setSpacing(10);
					vBox.setStyle("-fx-background-color: transparent");
					final ScrollPane content = new ScrollPane(vBox);
					content.setStyle("-fx-background-color: transparent");

					final Tab tab = new Tab(category.name(), content);
					tab.setClosable(false);
					tabPane.getTabs().add(tab);
					categoryVBoxMap.put(category, vBox);
				}
				fieldConsumer.accept(field, categoryVBoxMap.get(category));
			}
		});


	}

	private void addSliderSelector(Field field, VBox vBox)
	{
		final SliderSelector selector = field.getAnnotation(SliderSelector.class);
		Slider slider = new Slider();
		slider.setMax(selector.max());
		slider.setMin(selector.min());

		addComponent(field, vBox, slider, Double.class, Slider::setValue, Slider::valueProperty);
	}


	private void addMenuSelector(Field field, VBox vBox)
	{
		final String[] options = field.getAnnotation(MenuSelector.class).options();
		final ChoiceBox<String> stringChoiceBox = new ChoiceBox<>();
		for (String option : options)
			stringChoiceBox.getItems().add(option);
		addComponent(field, vBox, stringChoiceBox, String.class, ChoiceBox::setValue, ChoiceBox::valueProperty);
	}


	private void addBooleanSelector(Field field, VBox vBox)
	{
		final JFXToggleButton button = new JFXToggleButton();
		addComponent(field, vBox, button, Boolean.class, ToggleButton::setSelected, ToggleButton::selectedProperty);
	}

	private <T extends Control, M> void addComponent(Field field, VBox vBox, T control, Class<M> type, BiConsumer<T, M> consumer, Function<T, ObservableValue<?>> observableValue)
	{
		final Label label = new Label(field.getName());
		label.setStyle("-fx-text-fill: #14b3a6");
		ReflectionHelper.getField(type, field).ifPresent(value -> consumer.accept(control, value));
		observableValue.apply(control).addListener((observable, oldValue, newValue) -> ReflectionHelper.setField(field, newValue));
		final HBox holder = new HBox(label, control);
		holder.setAlignment(Pos.CENTER);
		holder.setSpacing(10);
		vBox.getChildren().add(holder);
	}

}
