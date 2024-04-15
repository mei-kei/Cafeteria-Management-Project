/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author muneeraaltheeb
 */
public class cafeteria {
}
    def__init__(self):
        self.food_catalog = {}

    def add_food(self, food_name, country_of_origin):
        """
        Add a new variety of food to the cafeteria's food catalog.
        """
        food_code = self.generate_food_code(food_name)
        self.food_catalog[food_code] = {
            "name": food_name,
            "country_of_origin": country_of_origin
        }
        print(f"Added new food: {food_name} (Code: {food_code})")

    def generate_food_code(self, food_name):
        """
        Generate a unique code for a new food item.
        """
        return f"{food_name[:3].upper()}{len(self.food_catalog) + 1}"

    def get_food_info(self, food_code):
        """
        Inquire about the information of a specific food.
        """
        if food_code in self.food_catalog:
            food_info = self.food_catalog[food_code]
            print(f"Food Name: {food_info['name']}")
            print(f"Country of Origin: {food_info['country_of_origin']}")
        else:
            print(f"Food with code '{food_code}' not found in the catalog.")

# Example usage
cafeteria = Cafeteria()

# Add new foods
cafeteria.add_food("Chicken Sandwich", "USA")
cafeteria.add_food("Vegetable Salad", "Canada")
cafeteria.add_food("Beef Burrito", "Mexico")

# Get food information
cafeteria.get_food_info("CHI1")
cafeteria.get_food_info("VEG2")
cafeteria.get_food_info("BEE3")
cafeteria.get_food_info("NON4")  # Food not found


