using System.Runtime.Serialization;

namespace GeneticAlgorithm.Api
{
    [DataContract]
    public class CarView
    {
        [DataMember(Name="chassis")]
        public readonly Chassis Chassis = new Chassis();

        [DataMember(Name="wheel1")]
        public readonly Wheel Wheel1 = new Wheel();

        [DataMember(Name="wheel2")]
        public readonly Wheel Wheel2 = new Wheel();

        public override string ToString()
        {
            return "CarView{" +
                    "chassis=" + Chassis +
                    ", wheel1=" + Wheel1 +
                    ", wheel2=" + Wheel2 +
                    '}';
        }
    }
}
