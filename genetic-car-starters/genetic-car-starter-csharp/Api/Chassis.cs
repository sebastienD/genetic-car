using System.Runtime.Serialization;
using System.Collections.Generic;

namespace GeneticAlgorithm.Api
{
    [DataContract]    
    public class Chassis
    {
        [DataMember(Name="vecteurs")]
        public readonly List<float> Vecteurs = new List<float>();

        [DataMember(Name="densite")]
        public float Densite;

        public override string ToString()
        {
            return "Chassis{" +
                   "vecteurs=" + string.Join(",", Vecteurs) +
                   ", densite=" + Densite +
                   '}';
        }
    }
}