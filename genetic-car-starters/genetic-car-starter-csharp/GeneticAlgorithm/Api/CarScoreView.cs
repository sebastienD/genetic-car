using System.Runtime.Serialization;

namespace GeneticAlgorithm.Api
{
    [DataContract]
    public class CarScoreView
    {
        [DataMember(Name="car")]
        public CarView Car { get; set; }

        [DataMember(Name="score")]
        public float Score { get; set; }

        public override string ToString()
        {
            return "CarScoreView{" +
                    "car=" + Car +
                    ", score=" + Score +
                    '}';
        }

    }
}
