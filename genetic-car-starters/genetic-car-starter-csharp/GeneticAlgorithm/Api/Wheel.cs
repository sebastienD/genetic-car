namespace GeneticAlgorithm.Api
{
    public class Wheel
    {
        public float Radius;
        public float Density;
        public int Vertex;

        public override string ToString()
        {
            return "Wheel{" +
                   "radius=" + Radius +
                   ", density=" + Density +
                   ", vertex=" + Vertex +
                   '}';
        }
    }
}