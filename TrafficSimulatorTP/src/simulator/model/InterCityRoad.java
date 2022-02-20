package simulator.model;

public class InterCityRoad extends Road {

    InterCityRoad(String id, Junction srcJunction, Junction destJunction, int maxSpeed, int contLimit, int length, Weather weather) {
        super(id, srcJunction, destJunction, maxSpeed, contLimit, length, weather);
    }

    @Override
    void reduceTotalContamination() {

        int x;
        switch(this.getWeather()) {
            case SUNNY:
                x = 2;
                break;
            case CLOUDY:
                x = 3;
                break;
            case RAINY:
                x = 10;
                break;
            case WINDY:
                x = 15;
                break;
            case STORM:
                x = 20;
                break;
            default:
                x = 0;
                break;
        }

        this.totalContamination = ((100 - x) * this.totalContamination) / 100;
    }

    @Override
    void updateSpeedLimit() {
        this.currentSpeedLimit = this.getMaxSpeed();
        if (this.totalContamination > this.getContLimit()) {
            this.currentSpeedLimit = (int) this.getMaxSpeed() / 2;
        }

    }

    @Override
    int calculateVehicleSpeed(Vehicle v) {
        if (this.getWeather() == Weather.STORM) {
            return (this.getSpeedLimit() * 8) / 10;
        }
        return this.getSpeedLimit();
    }
}
