using GeneticAlgorithm.Api;
using System.Collections.Generic;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Runtime.Serialization.Json;
using System.Threading.Tasks;
using System.IO;
using System.Text;

namespace GeneticAlgorithm.RestClient
{
    public class RestClient {
        public string URL {get; set;}

        public string MyTeam {get; set;}

        public async Task<IEnumerable<CarScoreView>> Submit(IReadOnlyCollection<CarView> cars) {
            var client = new HttpClient();
            var carViewSerializer = new DataContractJsonSerializer(typeof(List<CarView>));
            
            client.DefaultRequestHeaders.Accept.Clear();
            client.DefaultRequestHeaders.Accept.Add(
                new MediaTypeWithQualityHeaderValue("application/json"));
            client.DefaultRequestHeaders.Add("User-Agent", ".NET Foundation Repository Reporter");

            MemoryStream bodyCarsStream = new MemoryStream();
            carViewSerializer.WriteObject(bodyCarsStream, cars);
            var bodyContent = Encoding.UTF8.GetString(bodyCarsStream.ToArray());
            var body = new StringContent(bodyContent, Encoding.UTF8, "application/json");
            System.Diagnostics.Debug.WriteLine(bodyContent);

            var postTask = client.PostAsync(URL + "/simulation/evaluate/" + MyTeam, body);

            var result = (await postTask).Content.ReadAsStreamAsync().Result;
            var carScoreViewSerializer = new DataContractJsonSerializer(typeof(List<CarScoreView>));
            var carScoreViews = carScoreViewSerializer.ReadObject(result) as List<CarScoreView>;

            return carScoreViews;
        }
    }
}