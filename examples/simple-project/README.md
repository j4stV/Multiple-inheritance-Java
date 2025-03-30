# Simple Project Example

This project demonstrates the use of the Java Multiple Inheritance framework.

## Project Structure

- `com.example.Vehicle` - root interface marked with `@Root` annotation
- `com.example.Car` - base class for car (extends generated `VehicleRoot`)
- `com.example.Boat` - base class for boat (extends generated `VehicleRoot`)
- `com.example.Amphibian` - class with multiple inheritance, using `@Mixin({Car.class, Boat.class})`
- `com.example.Main` - demo application

## Running the Example

```bash
./gradlew run
```

This will compile the project and run the `Main` class, which creates an instance of `Amphibian` using `MixinFactory` and demonstrates multiple inheritance by calling methods that are executed along the inheritance chain. 