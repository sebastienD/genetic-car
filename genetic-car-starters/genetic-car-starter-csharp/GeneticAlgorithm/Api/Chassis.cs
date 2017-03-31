using System.Runtime.Serialization;
using System.Collections.Generic;

namespace GeneticAlgorithm.Api
{

    [DataContract]    
    public class Chassis
    {
        [DataMember]
        public readonly List<float> Vecteurs = new List<float>();

        [DataMember]
        public float Densite;

        public override string ToString()
        {
            return "Chassis{" +
                   "vecteurs=" + Vecteurs +
                   ", Densite=" + Densite +
                   '}';
        }
    }
}