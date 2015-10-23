using GeneticAlgorithm.Api;

namespace GeneticAlgorithm.algo
{
    public class Car
    {
        private readonly float[] _coords = new float[23];

        public static Car CreateFrom(CarView carView)
        {
            Car car = new Car();

            for (var i = 0; i < 16; i++)
            {
                car._coords[i] = carView.Chassi.Vecteurs[i];
            }

            car._coords[16] = carView.Chassi.Densite;

            car._coords[17] = carView.Wheel1.Density;
            car._coords[18] = carView.Wheel1.Radius;
            car._coords[19] = carView.Wheel1.Vertex;

            car._coords[20] = carView.Wheel2.Density;
            car._coords[21] = carView.Wheel2.Radius;
            car._coords[22] = carView.Wheel2.Vertex;

            return car;
        }

        public static Car Random()
        {
            Car car = new Car();
            car._coords[0] = CustomRandom.NextChassisAxis();
            car._coords[1] = 0F;
            car._coords[2] = CustomRandom.NextChassisAxis();
            car._coords[3] = CustomRandom.NextChassisAxis();
            car._coords[4] = 0F;
            car._coords[5] = CustomRandom.NextChassisAxis();
            car._coords[6] = -CustomRandom.NextChassisAxis();
            car._coords[7] = CustomRandom.NextChassisAxis();
            car._coords[8] = -CustomRandom.NextChassisAxis();
            car._coords[9] = 0F;
            car._coords[10] = -CustomRandom.NextChassisAxis();
            car._coords[11] = -CustomRandom.NextChassisAxis();
            car._coords[12] = 0;
            car._coords[13] = -CustomRandom.NextChassisAxis();
            car._coords[14] = CustomRandom.NextChassisAxis();
            car._coords[15] = -CustomRandom.NextChassisAxis();
            car._coords[16] = CustomRandom.NextChassisDensity();
            car._coords[17] = CustomRandom.NextWheelDensity();
            car._coords[18] = CustomRandom.NextWheelRadius();
            car._coords[19] = CustomRandom.NextVertex();
            car._coords[20] = CustomRandom.NextWheelDensity();
            car._coords[21] = CustomRandom.NextWheelRadius();
            car._coords[22] = CustomRandom.NextVertex();
            return car;
        }

        public CarView ToCarView()
        {
            CarView carView = new CarView();
            for (var i = 0; i < 16; i++)
            {
                carView.Chassi.Vecteurs.Add(_coords[i]);
            }
            carView.Chassi.Densite = _coords[16];

            carView.Wheel1.Density = _coords[17];
            carView.Wheel1.Radius = _coords[18];
            carView.Wheel1.Vertex = (int) (_coords[19]);

            carView.Wheel2.Density = _coords[20];
            carView.Wheel2.Radius = _coords[21];
            carView.Wheel2.Vertex = (int) (_coords[22]);

            return carView;
        }
    }
}