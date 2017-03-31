using System.Runtime.Serialization;

namespace GeneticAlgorithm.Api
{
    [DataContract]
    public class CarView
    {
        [DataMember]
        public readonly Chassis Chassis = new Chassis();

        [DataMember]
        public readonly Wheel Wheel1 = new Wheel();

        [DataMember]
        public readonly Wheel Wheel2 = new Wheel();
    }
}
