package simulator.model;

public class CityRoad extends Road {
    CityRoad(String id, Junction srcJunction, Junction destJunction, int maxSpeed, int contLimit, int length, Weather weather) {
        super(id, srcJunction, destJunction, maxSpeed, contLimit, length, weather);
    }

    @Override
    void reduceTotalContamination() {
        int x = 2;
        if (this.getWeather() == Weather.WINDY || this.getWeather() == Weather.STORM) {
            x = 10;
        }

        if (this.totalContamination >= x) {
            this.totalContamination -= x;
        }

    }

    @Override
    void updateSpeedLimit() { // doesnt do anything
    }

    @Override
    int calculateVehicleSpeed(Vehicle v) {
        return ((11 - v.getContClass()) * this.getSpeedLimit()) / 11;
    }
}
