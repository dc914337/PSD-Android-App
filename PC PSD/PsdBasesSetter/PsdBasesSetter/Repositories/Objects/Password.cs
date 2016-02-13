using System;
using Newtonsoft.Json;
using PsdBasesSetter.Repositories.Serializers;

namespace PsdBasesSetter
{
	[Serializable]
	[DataContract]
	public class Password
	{
		[DataMember]
		[JsonConverter(typeof(ByteArrayConverter))]
		public byte[] bytes{get;set;} = new byte[0];

		public Password ()
		{
			//for serialization
		}
	}
}

