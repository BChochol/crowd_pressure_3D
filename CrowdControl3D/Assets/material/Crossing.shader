Shader "Unlit/Test"
{
    Properties
    {
        _MainTex ("Texture", 2D) = "white" {}
        _Skew ("Skew", Float) = 0
    }
    SubShader
    {
        LOD 100
        
        Tags
        {
            "RenderType"="Opaque"
            "Queue"="Transparent"
            "DisableBatching" = "true"
        }

        Pass
        {
            //BLEND SrcAlpha OneMinusSrcAlpha
            Blend SrcAlpha OneMinusSrcAlpha
            Cull Off
            
            CGPROGRAM
            #pragma vertex vert
            #pragma fragment frag

            #include "UnityCG.cginc"

            sampler2D _MainTex;
            float _Skew;
            

            struct appdata
            {
                float4 vertex : POSITION;
                float3 normal : NORMAL;
                float2 uv : TEXCOORD0;
            };

            struct v2f
            {
                float4 vertex : SV_POSITION;
                float3 normal : TEXCOORD0;
                float2 uv : TEXCOORD1;
            };

            v2f vert (appdata v)
            {
                v.vertex.z += v.uv.x * _Skew - _Skew/2;
                
                v2f o;
                o.vertex =  UnityObjectToClipPos(v.vertex);
                o.uv = v.uv;
                return o;
            }

            float4 frag (v2f i) : SV_Target
            {;
                float4 color = tex2D(_MainTex, i.uv);
                
                return color;
            }
            ENDCG
        }
    }
}
