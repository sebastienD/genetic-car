using System.Runtime.Serialization;

namespace GeneticAlgorithm.Api
{
    [DataContract]
    public class Wheel
    {
        [DataMember(Name="radius")]
        public float Radius;

        [DataMember(Name="density")]
        public float Density;

        [DataMember(Name="vertex")]
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